package devices;

import org.junit.jupiter.api.*;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StandardDeviceTest {

    @Test
    @DisplayName("Device must specify a strategy")
    void testNonNullStrategy() {
        assertThrows(NullPointerException.class, () -> new StandardDevice(null));
    }

    @Nested
    class TestOnDummy {

        @Test
        @DisplayName("Device is initially off")
        void testInitiallyOff() {
            var dumbFailingStrategy = mock(FailingStrategy.class);
            var device = new StandardDevice(dumbFailingStrategy);
            assertFalse(device.isOn());
        }
    }

    @Nested
    class TestOnStubs {
        private Device device;
        private FailingStrategy stubFailingStrategy;

        @BeforeEach
        void init(){
            this.stubFailingStrategy = mock(FailingStrategy.class);
            this.device = new StandardDevice(this.stubFailingStrategy);
        }

        @Test
        @DisplayName("Device can be switched on")
        void testCanBeSwitchedOn() {
            when(stubFailingStrategy.attemptOn()).thenReturn(true);
            device.on();
            assertTrue(device.isOn());
        }

        @Test
        @DisplayName("Device won't switch off if failing")
        void testWontSwitchOn() {
            when(stubFailingStrategy.attemptOn()).thenReturn(false);
            assertThrows(IllegalStateException.class, () -> device.on());
        }
    }

    @Nested
    class TestOnFakes {
        private Device device;
        private FailingStrategy fakeFailingStrategy;

        @BeforeEach
        void init(){
            this.fakeFailingStrategy = mock(FailingStrategy.class);
            this.device = new StandardDevice(this.fakeFailingStrategy);
            when(fakeFailingStrategy.attemptOn()).thenReturn(true, true, false);
            when(fakeFailingStrategy.strategyName()).thenReturn("mock");
        }

        @Test
        @DisplayName("Device switch on and off until failing")
        void testSwitchesOnAndOff() {
            IntStream.range(0, 2).forEach(i -> {
                device.on();
                assertTrue(device.isOn());
                device.off();
                assertFalse(device.isOn());
            });
            assertThrows(IllegalStateException.class, () -> device.on());
        }

        @Test
        @DisplayName("Device correctly produces a toString")
        void testToString() {
            assertEquals("StandardDevice{failingStrategy=mock, on=false}", device.toString());
        }

    }

    @Nested
    class TestOnSpy {
        private Device device;
        private FailingStrategy mockFailingStrategy;

        @BeforeEach
        void init(){
            this.mockFailingStrategy = spy(new FailingStrategy() {
                @Override
                public boolean attemptOn() {
                    return true;
                }

                @Override
                public void reset() {
                }

                @Override
                public String strategyName() {
                    return "trivial";
                }
            });
            this.device = new StandardDevice(this.mockFailingStrategy);
            when(mockFailingStrategy.attemptOn()).thenReturn(true, true, false);
            when(mockFailingStrategy.strategyName()).thenReturn("mock");
        }

        @Test
        @DisplayName("Failing strategy is not called on off")
        void testNotReset() {
            this.device.off();
            verifyNoInteractions(this.mockFailingStrategy);
        }

        @Test
        @DisplayName("Resetting a device resets failing strategy")
        void testReset() {
            this.device.isOn();
            this.device.reset();
            assertFalse(this.device.isOn());
            verify(this.mockFailingStrategy).reset();
            verify(this.mockFailingStrategy, times(1));
        }
    }


    @Nested
    class TestOnMocks {
        private Device device;
        private FailingStrategy mockFailingStrategy;

        @BeforeEach
        void init(){
            this.mockFailingStrategy = spy(mock(FailingStrategy.class));
            this.device = new StandardDevice(this.mockFailingStrategy);
            when(mockFailingStrategy.attemptOn()).thenReturn(true, true, false);
            when(mockFailingStrategy.strategyName()).thenReturn("mock");
        }

        @Test
        @DisplayName("Failing strategy is not called on off")
        void testNotReset() {
            this.device.off();
            verifyNoInteractions(this.mockFailingStrategy);
        }

        @Test
        @DisplayName("Resetting a device resets failing strategy")
        void testReset() {
            this.device.isOn();
            this.device.reset();
            assertFalse(this.device.isOn());
            verify(this.mockFailingStrategy).reset();
            verify(this.mockFailingStrategy, times(1));
        }
    }

}