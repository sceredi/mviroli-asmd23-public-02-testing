package revision.devices

trait Device:
  val failingPolicy: FailingPolicy
  require(failingPolicy != null, "FailingPolicy cannot be null")

  def isOn: Boolean
  def turnOn(): Unit

object Device:
  def apply(failingPolicy: FailingPolicy): Device =
    new DeviceImpl(failingPolicy)

  private class DeviceImpl(override val failingPolicy: FailingPolicy)
      extends Device:

    private var on = false
    override def isOn: Boolean = on

    override def turnOn(): Unit =
      if !failingPolicy.attemptOn() then
        on = false
        throw IllegalStateException("FailingPolicy disallows turning on")
      else on = true

