package com.apigee.regex;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AddressRegexTest {
  private static final String TEST_RESOURCE_DIR = "src/test/resources";
  private static final Gson gson = new Gson();
  private static Pattern addressSafePattern;

  private static Map<String, Object> readAddressData() throws IOException, IllegalStateException {

    File testResourceDir = new File(TEST_RESOURCE_DIR);
    if (!testResourceDir.exists()) {
      throw new IllegalStateException("no test directory.");
    }

    File addressData = new File(testResourceDir, "addresses-us-all.json");
    if (!addressData.exists()) {
      throw new IllegalStateException("no address test file.");
    }

    String payload = new String(Files.readAllBytes(addressData.toPath()));
    StringReader reader = new StringReader(payload);
    Map<String, Object> map = gson.fromJson(reader, Map.class);
    return map;
  }

  @Parameters(name = "item {index}: \"{0}\"")
  public static Collection<Object[]> examples() throws IOException, IllegalStateException {
    Map<String, Object> map = readAddressData();

    List<Map<String, Object>> addresses = (List<Map<String, Object>>) map.get("addresses");
    return addresses.stream()
        .map(address -> new Object[] {(String) address.get("address1")})
        .collect(Collectors.toList());
  }

  @Parameter(0)
  public String input;

  @BeforeClass
  public static void init() throws IOException {
    Map<String, Object> map = readAddressData();
    addressSafePattern = Pattern.compile((String)map.get("regexToTest"));
  }

  @Test
  public void addressRegexTest() throws Exception {
    Matcher m = addressSafePattern.matcher(input);
    Boolean found = m.find();
    Assert.assertFalse((found)?String.format("flagged \"%s\" (%s)", input, m.group(1)): input, found);
  }
}
