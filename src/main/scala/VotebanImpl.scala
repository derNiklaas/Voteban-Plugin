import org.codeoverflow.chatoverflow.api.io.dto.chat.twitch.TwitchChatMessage
import org.codeoverflow.chatoverflow.api.plugin.{PluginImpl, PluginManager}

import scala.util.Random

class VotebanImpl(manager: PluginManager) extends PluginImpl(manager) {

  private val twitchChatInput = require.input.twitchChat("twitchIn", "Twitch Input", false)
  private val twitchChatOutput = require.output.twitchChat("twitchOut", "Twitch Output", false)

  private val fileInput = require.input.file("fileIn", "File Input", false)
  private val fileOutput = require.output.file("fileOut", "File Output", false)

  private var banReasons: Array[String] = Array("YOUR BANNED")

  override def setup(): Unit = {
    if (!fileOutput.get.exists("voteban/")) {
      fileOutput.get.createDirectory("voteban")
      val defaultBanMessages =
        """You're Out!
          |You'll NOT be back!
          |Haha rip lol
          |Game Over
          |You're not allowed to exist anymore
          |The ban is a lie
          |No u
          |The ban hammer has spoken!
          |Flying is not enabled on this server!
          |I wish I could ban you more than once
          |I am the Law
          |Not even a kiss from a prince will bring you back
          |See you later, alligator!
          |User in your channel was banned from the server!
          |An apple a day keeps $user away""".stripMargin
      val defaultConfig = "derniklaas"
      fileOutput.get.saveFile(defaultBanMessages, "voteban/banmessages.txt")
      fileOutput.get.saveFile(defaultConfig, "voteban/channel.txt")
    }
    val channel = fileInput.get.getFile("voteban/channel.txt").get.toLowerCase()
    twitchChatInput.get().setChannel(channel)
    twitchChatOutput.get().setChannel(channel)
    twitchChatInput.get().registerChatMessageReceiveEventHandler(msg => messageHandler(msg.getMessage))
    if (fileOutput.get.exists("voteban/banmessages.txt")) {
      val reasons = fileInput.get.getFile("voteban/banmessages.txt").get
      banReasons = reasons.split("\n")
    }
    log("Started")
  }

  override def loop(): Unit = {}

  override def shutdown(): Unit = {
    log("Shutting down.")
  }

  def messageHandler(message: TwitchChatMessage): Unit = {
    var msg = message.getMessage
    if (msg.startsWith("!")) {
      handleCommand(message)
    }
  }

  def handleCommand(message: TwitchChatMessage): Unit = {
    var msg = message.getMessage
    msg = msg.splitAt(1)._2
    msg.split(" ")(0).toLowerCase() match {
      case "voteban" =>
        var splits = message.getMessage.split(" ")
        if (splits.length == 2) {
          val username = splits(1)
          if (message.getAuthor.getDisplayName == username.toLowerCase) {
            twitchChatOutput.get.sendChatMessage(s"$username wants to ban him-/herself BibleThump")
            return
          }
          var banReason = getRandomBanReason
          banReason = banReason.replace("$user", username)
          twitchChatOutput.get.sendChatMessage(s"${message.getAuthor.getDisplayName} banned $username. Reason: $banReason")
        }
    }
  }

  def getRandomBanReason: String = {
    val length = banReasons.length
    val random = new Random()
    val randomInt = random.nextInt(length)
    banReasons(randomInt)
  }
}


