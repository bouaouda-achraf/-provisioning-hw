import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.voxloud.provisioning.ProvisioningApplication;
import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.repository.DeviceRepository;
import com.voxloud.provisioning.service.IProvisioningService;

@RunWith(SpringRunner.class)
@SpringBootTest(
		  classes = ProvisioningApplication.class)
@TestPropertySource(
		  locations = "classpath:application.properties")
public class DeviceTest {

	@Autowired
	IProvisioningService iProvisioningService;
	
	@Autowired
	DeviceRepository deviceRepository;
	
	@Test
	public void testDesk() {
		String macAddress = "aa-bb-cc-dd-ee-ff";
		Device device = deviceRepository.findById(macAddress).get();
		Map<String, Object> map = iProvisioningService.constructMapFromAllSources(device);
        assertEquals(map.get("username"), "john");
        assertEquals(map.get("password"), "doe");
	}
	
	@Test
	public void testConference() {
		String macAddress = "f1-e2-d3-c4-b5-a6";
		Device device = deviceRepository.findById(macAddress).get();
		Map<String, Object> map = iProvisioningService.constructMapFromAllSources(device);
        assertEquals(map.get("username"), "sofia");
        assertEquals(map.get("password"), "red");
	}
	
	@Test
	public void testDeskWithOverideFragment() {
		String macAddress = "a1-b2-c3-d4-e5-f6";
		Device device = deviceRepository.findById(macAddress).get();
		Map<String, Object> map = iProvisioningService.constructMapFromAllSources(device);
        assertEquals(map.get("username"), "walter");
        assertEquals(map.get("password"), "white");
        assertEquals(map.get("domain"), "sip.anotherdomain.com");
        assertEquals(map.get("port"), "5161");
        assertEquals(map.get("timeout"), "10");
	}
	
	@Test
	public void testConferenceWithOverideFragment() {
		String macAddress = "1a-2b-3c-4d-5e-6f";
		Device device = deviceRepository.findById(macAddress).get();
		Map<String, Object> map = iProvisioningService.constructMapFromAllSources(device);
		assertEquals(map.get("username"), "eric");
        assertEquals(map.get("password"), "blue");
        assertEquals(map.get("domain"), "sip.anotherdomain.com");
        assertEquals(map.get("port"), "5161");
        assertEquals(map.get("timeout"), 10);
	}

}
