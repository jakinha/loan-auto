package cn.web.util;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import cn.web.dto.SSHCommand;

import org.apache.log4j.Logger;
import java.io.*;
import java.util.List;

/**
 * SSH Client is used for doing cli operation via ssh connection with either password auth or private key auth method
 */
public class SSHClient {
	
    private static final Logger logger = Logger.getLogger(SSHClient.class.getName());

    private boolean passwordAuth = true;
    private int sshPort = 22;

    private String testbed;
    private String sshUsername;
    private String sshPassword;
    private String sshPrivateKey;


    private static Session session;
    private static Channel channel;

    private int commandExitStatus = -1;


    /**
     * SSHClient instance with default ssh port = 22
     *
     * @param testbed     the ip or hostname of the testbed to start ssh connection with
     * @param sshUsername the ssh username
     * @param sshPassword the ssh password
     */
    public SSHClient(String testbed, String sshUsername, String sshPassword) {
        this.testbed = testbed;
        this.sshUsername = sshUsername;
        this.sshPassword = sshPassword;
    }

    /**
     * SSHClient instance with custom ssh port
     *
     * @param testbed     the ip or hostname of the testbed to start ssh connection with
     * @param sshUsername the ssh username
     * @param sshPassword the ssh password
     * @param sshPort     the ssh port
     */
    public SSHClient(String testbed, String sshUsername, String sshPassword, int sshPort) {
        this.testbed = testbed;
        this.sshUsername = sshUsername;
        this.sshPassword = sshPassword;
        this.sshPort = sshPort;

    }

    /**
     * SSHClient with private key auth and default ssh port = 22
     *
     * @param testbed       the ip or hostname of the testbed to start ssh connection with
     * @param sshUsername   the ssh username
     * @param sshPrivateKey the ssh private key used for key-based auth
     * @param sshPassword   the ssh private key encryption passphrase if any
     */
    public SSHClient(String testbed, String sshUsername, String sshPrivateKey, String sshPassword) {
        this.testbed = testbed;
        this.sshUsername = sshUsername;
        this.sshPrivateKey = sshPrivateKey;
        this.sshPassword = sshPassword;
        this.passwordAuth = false;
    }

    /**
     * SSHClient with private key auth and custom ssh port
     *
     * @param testbed       the ip or hostname of the testbed to start ssh connection with
     * @param sshUsername   the ssh username
     * @param sshPrivateKey the ssh private key used for key-based auth
     * @param sshPassword   the ssh private key encryption passphrase if any
     * @param sshPort       the ssh port
     */
    public SSHClient(String testbed, String sshUsername, String sshPrivateKey, String sshPassword, int sshPort) {
        this.testbed = testbed;
        this.sshUsername = sshUsername;
        this.sshPrivateKey = sshPrivateKey;
        this.sshPassword = sshPassword;
        this.passwordAuth = false;
        this.sshPort = sshPort;
    }


    /**
     * Init ssh session
     *
     * @throws Exception
     */
    private void init() throws Exception {
        logger.info("Started ssh connection to the server: " + this.testbed);
        JSch jsch = new JSch();

        if (!this.passwordAuth) {
            if (this.sshPassword == null || this.sshPassword.trim().equals("")) {
                jsch.addIdentity(this.sshPrivateKey);
            } else {
                jsch.addIdentity(this.sshPrivateKey, this.sshPassword);
            }
        }

        session = jsch.getSession(this.sshUsername, this.testbed, this.sshPort);

        if (this.passwordAuth) {
            session.setPassword(this.sshPassword);
        }

        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
    }

    /**
     * Execute single command via ssh connection.
     * Note that the single command should have no subsequent input needed to the end of its execution,
     * such as "uname -a" or "pwd" and etc.
     *
     * @param cmd the command to execute
     * @return the output of command
     * @throws Exception
     */
    public String execute(String cmd) throws Exception {
        try {
            this.init();
            logger.info("Executed command...\n" + cmd);
            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(cmd);
            InputStream in = channel.getInputStream();
            channel.connect();
            String output = this.readExecResult(in);
            logger.info("Received response...\n" + output);
            this.setCommandExitStatus(channel.getExitStatus());
            return output;

        } finally {
            session.disconnect();
            channel.disconnect();
        }
    }

    /**
     * Execute multiple commands interactively via ssh connection.
     * Usually this is used when the initial command needs subsequent input such as confirm or choice before it terminates execution,
     * for example, running the command of "passwd userid" will require further input for users to enter password twice.
     * <p>
     * NOTE that you cannot execute a single command from the method even if you put the single command into a Command object with command string and expected result!!!
     * To execute a single command, use the executeCommand() method
     *
     * @param commands list of commands to execute
     * @throws Exception
     */
    public void execute(List<SSHCommand> commands) throws Exception {
        try {
            this.init();
            logger.info("Executed commands interactively...");
            channel = session.openChannel("shell");

            channel.setOutputStream(System.out);
            InputStream in = channel.getInputStream();
            OutputStream out = channel.getOutputStream();
            channel.connect();
            for (SSHCommand command : commands) {
                String cmd = command.getCommand();
                logger.info("Sent command: " + cmd);
                cmd += "\n";
                out.write(cmd.getBytes());
                out.flush();
                Thread.sleep(5000);
                String output = this.readExecResultUntilPattern(in, command.getExpected());
                logger.info("Received response...\n" + output);
            }
            this.setCommandExitStatus(channel.getExitStatus());

        } finally {
            session.disconnect();
            channel.disconnect();
        }
    }

    /**
     * Get the last command exit status
     *
     * @return the command exit status as an integer number
     */
    public int getCommandExitStatus() {
        return this.commandExitStatus;
    }

    /**
     * Set the command exit status
     *
     * @param commandExitStatus the command exit status in an integer
     */
    private void setCommandExitStatus(int commandExitStatus) {
        this.commandExitStatus = commandExitStatus;
    }

    /**
     * Read from input stream to get command exec result
     *
     * @param in InputStream
     * @return output result in string
     * @throws IOException
     */
    private String readExecResult(InputStream in) throws IOException {
        StringBuilder output = new StringBuilder();
        char[] buf = new char[512];
        int nRead;
        BufferedReader inReader = new BufferedReader(new InputStreamReader(in));
        while ((nRead = inReader.read(buf, 0, buf.length)) > 0) {
            output.append(buf, 0, nRead);
        }
        return output.toString();
    }


    /**
     * Read from input stream to get command exec result until it matches given pattern
     *
     * @param in InputStream from connection channel
     * @param pattern the pattern to read until
     * @return output result in string
     * @throws Exception
     */
    private String readExecResultUntilPattern(InputStream in, String pattern) throws Exception {
        logger.info("Read the results until matching pattern: " + pattern);
        StringBuilder output = new StringBuilder();
        char lastChar = pattern.charAt(pattern.length() - 1);
        char ch = (char) in.read();
        while (true) {
            output.append(ch);
            if (ch == lastChar) {
                if (output.toString().endsWith(pattern)) {
                    return output.toString();
                }
                // else if () {} // placeholder for supporting regular expression match if needed
            }
            ch = (char) in.read();
        }
    }


/*    public static void main(String[] args) throws Exception {
        SSHClient client = new SSHClient("192.168.11.85", "root", "sit123456");
        client.execute("pwd");

        List<SSHCommand> commands = new ArrayList<SSHCommand>();
        SSHCommand command = new SSHCommand("pwd", "root");
        commands.add(command);
        SSHCommand command1 = new SSHCommand("cat /var/lib/jenkins/workspace/sit1-crm/pom.xml","project>");
        commands.add(command1);
        client.execute(commands);
    }*/

}
