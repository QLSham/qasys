package com.ps.qa.util;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

public class ZkLock {
    /**
     * 公共配置 : zk主机
     */
    private String HOST = "192.168.3.102:2181";

    /**
     * 公共配置: 锁的目录
     */
    private String ROOT = "/dlamgroup";

    /**
     * zk 客户端对象
     */
    private ZooKeeper zooKeeper;

    /**
     * 创建锁对象的名字
     */
    private String lockName;


    /**
     * 创建的临时节点名字
     */
    private String elockName;

    /**
     * 锁的全路径
     */
    private String lockNamePath;

    private ZkLock() {
    }

    public ZkLock(String lockName) throws IOException, KeeperException, InterruptedException {
        this.lockName = lockName;
        this.lockNamePath = ROOT + "/" + lockName;
        zooKeeper = new ZooKeeper(HOST, 10000, null);
        if (zooKeeper.exists(ROOT, false) == null) {
            zooKeeper.create(ROOT, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        if (zooKeeper.exists(lockNamePath, false) == null) {
            zooKeeper.create(lockNamePath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

    }

    public boolean getLock() throws KeeperException, InterruptedException {
        // 第一步： 创建临时有序节点 ， 并返回一个新的节点名字
        elockName = zooKeeper.create(lockNamePath + "/x", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        System.out.println("成功创建临时节点: " + elockName);

        while (true) {
            // 第二步： 获取子节点
            List<String> subNodes = zooKeeper.getChildren(lockNamePath, false);

            if (null == subNodes) {
                return false;
            }
            System.out.println("获取的所有子节点: " + subNodes);

            String firstNodeName = subNodes.stream().filter((path) -> path.startsWith("x")).sorted().findFirst().get();

            // 第四步：判断是否是最小节点，  如果是则执行
            System.out.println("当前进程节点：  " + elockName + "   第一个节点:" + firstNodeName);

            if (elockName.equals(lockNamePath + "/" + firstNodeName)) {
                return true;
            }

            // 如果不是第一个节点，则监视第一个节点;
            Stat stat = zooKeeper.exists(lockNamePath + "/" + firstNodeName, true);

            // 如果最小节点不存在，有可能已经被删除，这里重新执行2,3,4步骤
            if (stat == null) {
                continue;
            }

            // 当前线程等待 ， 等待其它进程释放锁
            synchronized (lockName) {
                System.out.println(elockName + "等待锁");
                // 最多等20秒，防止第一个节点被删除又没有监控到
                lockName.wait(20000);
            }

        }


    }

    /**
     * 释放锁
     *
     * @throws InterruptedException
     * @throws KeeperException
     */
    public void unlock() throws InterruptedException, KeeperException {
        System.out.println(elockName + "释放锁 ");
        zooKeeper.delete(elockName, -1);
    }

    /**
     * 一个观察者对象，用于观察节点删除事件
     */
    private class NodeDelWatcher implements Watcher {

        @Override
        public void process(WatchedEvent event) {
            // 不为删除事件则退出
            if (event.getType() != Event.EventType.NodeDeleted) {
                return;
            }

            // 通知挂起的线程恢复执行
            synchronized (lockName) {
                System.out.println(lockName + " 接受到有进程释放锁,唤醒等待的线程");
                lockName.notifyAll();
            }
        }
    }
}
