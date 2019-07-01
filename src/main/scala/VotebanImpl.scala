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
    twitchChatInput.get().setChannel("#skate702")
    twitchChatOutput.get().setChannel("#skate702")
    twitchChatInput.get().registerMessageHandler(msg => messageHandler(msg))
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


