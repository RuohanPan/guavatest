package testGuava;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;

import java.io.File;
import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ruohanpan on 2017/5/15.
 */
public class IOTest {
    public void fileWrite(String fileName, String contents) {
        checkNotNull(fileName, "Provided file name for writing must not be null");
        checkNotNull(contents, "contents must not be null");
        final File newFile = new File(fileName);
        try {
            Files.write(contents.getBytes(), newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class CounterLine implements LineProcessor<Integer>{
        private int rowNum = 0;

        public boolean processLine(String s) throws IOException {
            rowNum++;
            return true;
        }

        public Integer getResult() {
            return rowNum;
        }
    }
    /*复制文件*/
    public void demoSimpleFileCopy(String sourceFileName, String targetFileName) {
        checkNotNull(sourceFileName, "Copy source file name must NOT be null.");
        checkNotNull(targetFileName, "Copy target file name must NOT be null.");
        final File sourceFile = new File(sourceFileName);
        final File targetFile = new File(targetFileName);
        try {
            Files.copy(sourceFile, targetFile);
        } catch (IOException fileIoEx) {
            fileIoEx.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String testFilePath = "/User/ruohanpan/Downloads/1.rtf";
        File testFile = new File(testFilePath);
        CounterLine counterLine = new CounterLine();
        try {
            Files.readLines(testFile, Charsets.UTF_8, counterLine);
            System.out.println(counterLine.getResult());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
