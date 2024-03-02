package revision.devices

trait Device:
  val failingPolicy: FailingPolicy
  require(failingPolicy != null, "FailingPolicy cannot be null")

  val isOn: Boolean

object Device:
  def apply(failingPolicy: FailingPolicy): Device =
    new DeviceImpl(failingPolicy)


  private class DeviceImpl(override val failingPolicy: FailingPolicy) extends Device:
    private val on = false
    override val isOn: Boolean = on