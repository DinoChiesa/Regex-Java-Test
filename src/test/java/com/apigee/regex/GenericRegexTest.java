package com.apigee.regex;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class GenericRegexTest {
  private static final String TEST_RESOURCE_DIR = "src/test/resources";

  private static final Gson gson = new Gson();

  protected static String getFileContents(String filename) throws Exception {
    Path path = Paths.get(TEST_RESOURCE_DIR, filename);
    if (!Files.exists(path)) {
      return null;
    }
    return new String(Files.readAllBytes(path));
  }

  @Parameters(name = "{index}: {1} ({0})")
  public static Collection<Object[]> examples() throws IOException, IllegalStateException {
    File testResourceDir = new File(TEST_RESOURCE_DIR);
    if (!testResourceDir.exists()) {
      throw new IllegalStateException("no test directory.");
    }

    // File testDefinition = new File(testResourceDir, "tests.json");

    File[] files = testResourceDir.listFiles();
    Arrays.sort(files);
    if (files.length == 0) {
      throw new IllegalStateException("no tests found.");
    }

    List<Object[]> list =
        Arrays.asList(files).stream()
            .filter(file -> file.getName().endsWith(".json") && file.getName().startsWith("test"))
            .map(
                file -> {
                  try {
                    String name = file.getName();
                    String payload = new String(Files.readAllBytes(file.toPath()));
                    StringReader reader = new StringReader(payload);
                    Map<String, Object> map = gson.fromJson(reader, Map.class);
                    return new Object[] {
                      (String) map.get("regex"), (String) map.get("description"), map.get("cases")
                    };
                  } catch (IOException ioexc) {
                    throw new UncheckedIOException(ioexc);
                  }
                })
            .collect(Collectors.toList());

    return list; // .stream().toArray(Object[][]::new);

    // return Arrays.asList(new Object[][] {
    //     {"", false, "empty string"},
    //     {"a", false, "single non-digit"},
    //     {"1", true, "single digit"},
    //     {"123", true, "integer"},
    //     {"-123", true, "integer, negative sign"},
    //     {"+123", true, "integer, positive sign"},
    //     {"123.12", true, "float"},
    //     {"123.12e", false, "float with exponent extension but no value"},
    //     {"123.12e12", true, "float with exponent"},
    //     {"123.12E12", true, "float with uppercase exponent"},
    //     {"123.12e12.12", false, "float with non-integer exponent"},
    //     {"123.12e+12", true, "float with exponent, positive sign"},
    //     {"123.12e-12", true, "float with exponent, negative sign"},
    // });

  }

  @Parameter(0)
  public String regex;

  @Parameter(1)
  public String description;

  @Parameter(2)
  public ArrayList cases;

  @Test
  public void regexTest() throws Exception {
    System.out.printf("test set: %s\n", description);
    IntStream.range(0, cases.size())
        .forEach(
            i -> {
              ArrayList testcase = (ArrayList) cases.get(i);
              // System.out.printf("testcase type: %s\n",
              //                   testcase.getClass().getName());
              String input = (String) testcase.get(0);
              Boolean expectedResult = (Boolean) testcase.get(1);
              String description = (String) testcase.get(2);
              Boolean matches = input.matches(regex);
              // Assert.assertEquals(String.format("case %d", i), expectedResult,matches);
              boolean pass = (expectedResult == matches);
              System.out.printf("  case %3d: %s\n", i, pass ? "PASS" : "FAIL");
              Assert.assertTrue(pass);
            });
  }
}
