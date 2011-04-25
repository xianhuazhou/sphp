package study.zhou

object Logger {
  def log(message: String) {
    if (Config.isDebugMode) println(message)
  }
}

// vim: set ts=2 sw=2 et:
