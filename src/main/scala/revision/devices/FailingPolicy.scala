package revision.devices

trait FailingPolicy:
  def attemptOn(): Boolean
