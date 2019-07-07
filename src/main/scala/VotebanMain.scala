import org.codeoverflow.chatoverflow.api.plugin.{Pluggable, Plugin, PluginManager}

class VotebanMain extends Pluggable{

  override def getName: String = "Voteban"

  override def getAuthor: String = "derNiklaas"

  override def getDescription: String = "The best Voteban bot around this city!"

  override def getMajorAPIVersion: Int = 2

  override def getMinorAPIVersion: Int = 0

  override def createNewPluginInstance(manager: PluginManager): Plugin = new VotebanImpl(manager)
}
