import java.util.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

public class Main {

    public static void main(String[] args) {
        List<String> listMock = mock(List.class);
        when(listMock.size()).thenReturn(5);
        System.out.println(listMock.size());
    }
}
