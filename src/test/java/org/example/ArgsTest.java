package org.example;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ArgsTest {

    // -l -p 8080 -d /usr/logs　每个参数都可以支持数组

    // 第一步：编写出基本结构确定 API 的形式，使编译可以通过；
    @Test
    // 真正红绿循环的时候，这个测试用例是不需要的，因为这个测试用例是为了帮助我们思考实现方式的，所以这个测试用例可以禁用
    @Disabled
    public void should() {
        Options options = Args.parse(Options.class, "-l", "-p", "8080", "-d", "/usr/logs");
        options.logging();
        options.port();
        options.directory();
    }
    // 第二步：确定 API 的形式之后，思考实现方式；
    // 参数分组
    // [-l] [-p, 8080] [-d, /usr/logs]
    // 参数按字典归集
    // {-l:[], -p:[], -d:[]}


    // 第三步：将例子放入 最终实现
    // 多个参数：-l -p 8080 -d /usr/logs　
    @Test
    // 真正红绿循环的时候，这个测试用例是不需要的，因为这个测试用例是为了帮助我们思考实现方式的，所以这个测试用例可以禁用
    @Disabled
    public void multi_param() {
        Options parse = Args.parse(Options.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertTrue(parse.logging());
        assertEquals("8080", parse.port());
        assertEquals("/usr/logs", parse.directory());
    }

    // 数组参数：-g this is a list -d 1 2 -3 5　"g"表示一个字符串列表[“this”, “is”, “a”, “list”]，“d"标志表示一个整数列表[1, 2, -3, 5]。　
    @Test
    // 真正红绿循环的时候，这个测试用例是不需要的，因为这个测试用例是为了帮助我们思考实现方式的，所以这个测试用例可以禁用
    @Disabled
    public void list_param() {
        ListOptions parse = Args.parse(ListOptions.class, "-g this is a list -d 1 2 -3 5");
        assertEquals(new String[]{"this", "is", "a", "list"}, parse.group());
        assertEquals(new int[]{1, 2, -3, 5}, parse.decimals());
    }


    @Test
    public void should_bool_option_to_false_if_flag_present() {
        BooleanOption options = Args.parse(BooleanOption.class);
        assertFalse(options.logging());
    }

    // 第四步：todoList
    // Single option:
    // TODO     - Bool: -l
    // TODO     - Int: -p 8080
    // TODO     - String: -d /usr/logs
    // TODO Multi option: -l -p -d
    // Sad Path:
    // TODO     - Bool: -l f / -l 0
    // TODO     - Int: -p 8080.1 / -p abc
    // TODO     - String: -d /usr/logs/ / -d /usr/logs
    // default Value:
    // TODO     - Bool: -l true
    // TODO     - Int: -p 0
    // TODO     - String: -d ""


    static record Options(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
    }
    // TDD，一次只做一点点；

    static record ListOptions(@Option("g") String[] group, @Option("d") int[] decimals) {
    }

    // 第五步：编写 todoList 中的单元测试
    // 如果没有失败的测试，不要编写功能代码
    // Single option:
    // TODO     - Bool: -l
    record BooleanOption(@Option("l") boolean logging) {
    }
    @Test
    public void should_bool_option_to_true_if_flag_present() {
        BooleanOption options = Args.parse(BooleanOption.class, "-l");
        assertTrue(options.logging());
    }
    // TODO     - Int: -p 8080
    record IntOption(@Option("p") int port) {
    }
    @Test
    public void should_parse_int_as_option_value() {
        IntOption options = Args.parse(IntOption.class, "-p", "8080");
        assertEquals(8080, options.port());
    }
    // TODO     - String: -d /usr/logs
    record StringOption(@Option("d") String directory) {
    }
    @Test
    public void should_parse_string_as_option_value() {
        StringOption options = Args.parse(StringOption.class, "-d", "/usr/logs");
        assertEquals("/usr/logs", options.directory());
    }
    // TODO Multi option: -l -p 8080 -d /usr/logs
    record MultiOption(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
    }
    @Test
    public void should_parse_multi_param_as_option_value(){
        MultiOption parse = Args.parse(MultiOption.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertTrue(parse.logging());
        assertEquals(8080, parse.port());
        assertEquals("/usr/logs", parse.directory());
    }


    // TODO     - Int: -p 8080.1 / -p abc
    // TODO     - String: -d /usr/logs/ / -d /usr/logs
    // default Value:
    // TODO     - Bool: -l true
    // TODO     - Int: -p 0
    // TODO     - String: -d ""
}

