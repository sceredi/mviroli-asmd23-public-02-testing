package revision.devices

import org.junit.jupiter.api.Assertions.{assertFalse, assertThrows}
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock

class DeviceTest:
  @Test def aDeviceShouldHaveAFailingPolicy(): Unit =
    assertThrows(classOf[IllegalArgumentException], () => Device(null))

  @Test def aDeviceShouldStartOff(): Unit =
    val failingPolicy: FailingPolicy = mock(classOf[FailingPolicy])
    val device = Device(failingPolicy)
    assertFalse(device.isOn)
