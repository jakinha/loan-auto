package cn.web.dto;

/**
 * Command Bean
 */
public class SSHCommand {

    /**
     * Command
     */
    private String command;

    /**
     * Expected output after executing command
     */
    private String expected;

    public SSHCommand() {
    }

    public SSHCommand(String command, String expected) {
        this.command = command;
        this.expected = expected;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

}
