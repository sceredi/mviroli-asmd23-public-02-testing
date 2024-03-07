package revision.devices

import org.junit.jupiter.api.Assertions.{assertFalse, assertThrows, assertTrue}
import org.junit.jupiter.api.Test
import org.mockito.Mockito.{mock, when}

class DeviceTest:
  @Test def aDeviceShouldHaveAFailingPolicy(): Unit =
    assertThrows(classOf[IllegalArgumentException], () => Device(null))

  @Test def aDeviceShouldStartOff(): Unit =
    val failingPolicy: FailingPolicy = mock(classOf[FailingPolicy])
    val device = Device(failingPolicy)
    assertFalse(device.isOn)

  @Test def aDeviceShouldTurnOnOnlyIfTheFailingPolicyAllowsIt(): Unit =
    val failingPolicy: FailingPolicy = mock(classOf[FailingPolicy])
    when(failingPolicy.attemptOn()).thenReturn(true)
    val device = Device(failingPolicy)
    device.turnOn()
    assertTrue(device.isOn)
    device.turnOn()
    assertTrue(device.isOn)
    device.turnOn()
    assertTrue(device.isOn)
