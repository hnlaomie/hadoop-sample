package com.github.sample.zookepper;

/**
 * @author <a href="mailto:hnlaomie@hotmail.com">laomie</a>
 * @version V1
 * @date 2014-11-26 11:00
 */
import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.KeeperException.ConnectionLossException;
import org.apache.zookeeper.data.Stat;

import java.util.Random;

public class Master implements Watcher {
    private ZooKeeper zk;
    private String hostPort;
    private static String serverId = Integer.toHexString(new Random().nextInt());
    private static boolean isLeader = false;

    Master(String hostPort) {
        this.hostPort = hostPort;
    }

    void startZK() throws Exception {
        zk = new ZooKeeper(hostPort, 15000, this);
    }

    void stopZK() throws Exception {
        zk.close();
    }

    public void process(WatchedEvent e) {
        System.out.println(e);
    }

    // returns true if there is a master
    boolean checkMaster() {
        while (true) {
            try {
                Stat stat = new Stat();
                byte data[] = zk.getData("/master", false, stat);
                isLeader = new String(data).equals(serverId);
                return true;
            } catch (KeeperException e) {
                // no master, so try create again
                return false;
            } catch (InterruptedException e) {

            }
        }
    }

    void runForMaster() throws InterruptedException {
        zk.create("/master", serverId.getBytes(), Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL, masterCreateCallback, null);
        System.out.println("synchronous create.");
    }

    public AsyncCallback.StringCallback masterCreateCallback = new AsyncCallback.StringCallback() {
        public void processResult(int rc, String path, Object ctx, String name) {
            System.out.println(KeeperException.Code.get(rc));
            switch(KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    checkMaster();
                    return;
                case OK:
                    isLeader = true;
                    break;
                default:
                    isLeader = false;
            }
            System.out.println("I'm " + (isLeader ? "" : "not ") +
                    "the leader");
        }
    };

    public static void main(String args[]) throws Exception {
        Master m = new Master(args[0]);
        m.startZK();
        m.runForMaster();
        if (isLeader) {
            System.out.println("I'm the leader");
            // wait for a bit
            Thread.sleep(30000);
        } else {
            System.out.println("Someone else is the leader");
        }
        m.stopZK();
    }
}
