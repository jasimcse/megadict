package com.megadict.format.dict.index.segment;

import static org.junit.Assert.*;

import org.junit.Test;

public class ByteBufferToolTest {

    private static final String rawContent = "A shares\tppv+\tBd\nA4-size\toOVN\t0\naa\tsnur\tBX\naaa\tsnw"
            + "C\tCE\naardvark\tsnyG\t1\naard-wolf\t5k\tBZ\naardwolf\tsny7\t9\naasvogel\t"
            + "69\tBX\nab\tsnz4\tB2\naba\t8U\tBd\nabac, abacus\t2GQg\t7\nabaca\tsn1u\tBc\n"
            + "abaci\t9x\tDb\nabacist\tsn3K\tBG\naback\tBBM\tFp\nabacterial\tsn4Q\t4\nab"
            + "action\tsn5I\tBH\nabactus\tsn6P\tp\nabacus\tBG1\tDc\nabacus\toOWB\tc\nabaddo"
            + "n\tBKR\tBI\nabaft\tBLZ\tDH\nabalienate\tsn64\tBA\nabalienation\tsn74\t8\nabal"
            + "one\tBOg\tBK\nabampere\toOWd\tCC\nabandon\tBPq\tGn\nabandon\toOYf\tn\nabandon "
            + "call\toOZG\ti\nabandoned\tBWR\tBr\nabandoner\tBX8\tBL\nabandonment\tBZH\tDi\nab"
            + "andonment\toOZo\tq\nabapikal\tsn80\tBM\nabarticular\tsn+A\tBP\nabase\tBcp\tCM\naba"
            + "sement\tBe1\tB1\nabash\tBgq\tBm\nabashed\tsn/P\tw\nabashment\tBiQ\tBk\nabask\tBj0\tB"
            + "z\nabatable\tBln\tEU\nabate\tBp7\tH4\nabatement\tre34\teA3";

    @Test
    public void testGetFirstHeadWord() {
        String expected = "A shares";
        String actual = ByteBufferTool.firstHeadWordIn(rawContent.getBytes(), 50);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetFirstHeadWordkWithEmptyInput() {
        String actual = ByteBufferTool.firstHeadWordIn(new byte[0], 50);
        assertEquals("", actual);
    }

    @Test
    public void testGetFirstHeadWordWithShortInput() {
        String shortInput = "ds8 23324 tra";
        String actual = ByteBufferTool.firstHeadWordIn(shortInput.getBytes(), 50);
        assertEquals("", actual);
    }

    @Test
    public void testGetLastHeadWord() {
        String givenContent = "A shares\tppv+\tBd\nA4-size\toOVN\t0\naa\tsnur\tBX\naaa\tsnw"
                + "C\tCE\naardvark\tsnyG\t1\naard-wolf\t5k\tBZ\naardwolf\tsny7\t9\naasvogel\t"
                + "69\tBX\nab\tsnz4\tB2\naba\t8U\tBd\nabac, abacus\t2GQg\t7\nabaca\tsn1u\tBc\n"
                + "abaci\t9x\tDb\nabacist\tsn3K\tBG\naback\tBBM\tFp\nabacterial\tsn4Q\t4\nab"
                + "action\tsn5I\tBH\nabactus\tsn6P\tp\nabacus\tBG1\tDc\nabacus\toOWB\tc\nabaddo"
                + "n\tBKR\tBI\nabaft\tBLZ\tDH\nabalienate\tsn64\tBA\nabalienation\tsn74\t8\nabal"
                + "one\tBOg\tBK\nabampere\toOWd\tCC\nabandon\tBPq\tGn\nabandon\toOYf\tn\nabandon "
                + "call\toOZG\ti\nabandoned\tBWR\tBr\nabandoner\tBX8\tBL\nabandonment\tBZH\tDi\nab"
                + "andonment\toOZo\tq\nabapikal\tsn80\tBM\nabarticular\tsn+A\tBP\nabase\tBcp\tCM\naba"
                + "sement\tBe1\tB1\nabash\tBgq\tBm\nabashed\tsn/P\tw\nabashment\tBiQ\tBk\nabask\tBj0\tB"
                + "z\nabatable\tBln\tEU\nabate\tBp7\tH4\nabatement\tre34\teA3";

        String expected = "abatement";

        String actual = ByteBufferTool.lastHeadWordIn(givenContent.getBytes());

        assertEquals(expected, actual);
    }

    @Test
    public void testGetLastHeadWordWithEmptyInput() {
        String actual = ByteBufferTool.lastHeadWordIn(new byte[0]);
        assertEquals("", actual);
    }

    @Test
    public void testGetLastHeadWordWithShortInput() {
        String shortInput = "ds8 23324 tra";
        String actual = ByteBufferTool.lastHeadWordIn(shortInput.getBytes());
        assertEquals("", actual);
    }

    @Test
    public void testExtractBufferLeftOver() {
        String expectedLeftOver = "abatement\tre34\teA3";

        byte[] actualLeftOver = ByteBufferTool.extractBufferLeftOver(rawContent.getBytes());

        String actualLeftOverInString = new String(actualLeftOver);

        assertEquals(expectedLeftOver, actualLeftOverInString);
    }

    @Test
    public void testCopyBackwardFromSourceOffset() {
        String givenSource = "This is the original content\nLeftOver"; // 29 + 8
                                                                       // chars
        String leftOver = "LeftOver";
        String dest = "OK, 12345678901234567890123456789";

        String expectedDest = "OK, This is the original content\n";

        byte[] actualDestInCharArray = ByteBufferTool.copyBackwardFromSourceOffset(givenSource.getBytes(),
                leftOver.length(), dest.getBytes());
        String actualDest = new String(actualDestInCharArray);

        assertEquals(expectedDest, actualDest);
    }

    @Test
    public void testCopyBackwardWithDestOffset() {
        String source = "This is the original content";
        String dest = "OK, ............................, after appending";

        String expectedDest = "OK, This is the original content, after appending";

        byte[] actualInByteArray = ByteBufferTool
                .copyBackwardToDestOffset(source.getBytes(), dest.getBytes(), ", after appending".length());
        String actualDest = new String(actualInByteArray);

        assertEquals(expectedDest, actualDest);
    }
}
