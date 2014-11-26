package scan;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.datalook.ezui.generate.plugin.scan.Scanner;

public class ScannerT {

	@Test
	public void testScan() {
		new Scanner().scan(new File("E:\\Workspaces\\sts\\mojoExe\\target\\classes"));
	}

}
