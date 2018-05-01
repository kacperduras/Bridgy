package pl.kacperduras.bridgy

import org.apache.commons.lang3.StringUtils
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import pl.kacperduras.commandbridge.shaded.CommandBridgeAPI

class BridgyPlugin : JavaPlugin() {

  val COMMAND_BRIDGE_PLUGIN_NAME = "commandbridge-bukkit"

  private lateinit var bridge: CommandBridgeAPI

  override fun onEnable() {
    if (!this.server.pluginManager.isPluginEnabled(COMMAND_BRIDGE_PLUGIN_NAME)) {
      this.isEnabled = false
      return
    }

    this.bridge = this.server.pluginManager.getPlugin(COMMAND_BRIDGE_PLUGIN_NAME) as CommandBridgeAPI
    this.getCommand("bridgy").executor = this
  }

  override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
    if (args.isEmpty()) {
      sender.sendMessage(this.color("&cCorrect usage: &7/bridgy <command>&c."))
      return true
    }

    if (!bridge.isBukkit) {
      sender.sendMessage(this.color("&cInternal error!"))
      return true
    }

    if (sender is Player) {
      bridge.bungee(sender.name, StringUtils.join(args, " "))
      return true
    }

    sender.sendMessage(this.color("&cYou must be a Player!"))
    return true
  }

  private fun color(string: String): String {
    return ChatColor.translateAlternateColorCodes('&', string)
  }

}
