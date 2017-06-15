package cn.tom.web.quartz;

/**
 * @author lxl
 */
public enum QuartzJobEnum {
    TEST_JOB("task test");

    private final String name;

    QuartzJobEnum(String name) {
        this.name = name.trim();
    }
}
