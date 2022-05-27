package indi.shenqqq.bbs.monitor;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.NumberUtil;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

import java.util.Properties;

/**
 * @Author Shen Qi
 * @Date 2022/4/20 14:10
 * @Description XX
 */
public class Server {
    private static final int OSHI_WAIT_SECOND = 1000;

    private CPU cpu = new CPU();

    private Memory mem = new Memory();

    private JVM jvm = new JVM();

    private System sys = new System();

    private Disk disk = new Disk();

    public CPU getCpu() {
        return cpu;
    }

    public void setCpu(CPU cpu) {
        this.cpu = cpu;
    }

    public Memory getMem() {
        return mem;
    }

    public void setMem(Memory mem) {
        this.mem = mem;
    }

    public JVM getJvm() {
        return jvm;
    }

    public void setJvm(JVM jvm) {
        this.jvm = jvm;
    }

    public System getSys() {
        return sys;
    }

    public void setSys(System sys) {
        this.sys = sys;
    }

    public Disk getDisk() {
        return disk;
    }

    public void setDisk(Disk disk) {
        this.disk = disk;
    }

    public void copyTo() throws Exception {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        setCpuInfo(hal.getProcessor());

        setMemInfo(hal.getMemory());

        setSysInfo();

        setJvmInfo();

        setDisk(si.getOperatingSystem());
    }

    private void setCpuInfo(CentralProcessor processor) {
        // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(OSHI_WAIT_SECOND);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
        long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
        long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
        long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
        long cSys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
        long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
        long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
        long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        cpu.setCpuNum(processor.getLogicalProcessorCount());
        cpu.setTotal(totalCpu);
        cpu.setSys(cSys);
        cpu.setUsed(user);
        cpu.setWait(iowait);
        cpu.setFree(idle);
    }

    private void setMemInfo(GlobalMemory memory) {
        mem.setTotal(memory.getTotal());
        mem.setUsed(memory.getTotal() - memory.getAvailable());
        mem.setFree(memory.getAvailable());
    }

    private void setSysInfo() {
        Properties props = java.lang.System.getProperties();
        sys.setComputerName(NetUtil.getLocalhost().getHostName());
        sys.setComputerIp(NetUtil.getLocalhost().getHostAddress());
        sys.setOsName(props.getProperty("os.name"));
        sys.setOsArch(props.getProperty("os.arch"));
        sys.setUserDir(props.getProperty("user.dir"));
    }

    private void setJvmInfo() {
        Properties props = java.lang.System.getProperties();
        jvm.setTotal(Runtime.getRuntime().totalMemory());
        jvm.setMax(Runtime.getRuntime().maxMemory());
        jvm.setFree(Runtime.getRuntime().freeMemory());
        jvm.setVersion(props.getProperty("java.version"));
        jvm.setHome(props.getProperty("java.home"));
    }

    private void setDisk(OperatingSystem os) {
        FileSystem fileSystem = os.getFileSystem();
        OSFileStore[] fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            if (java.lang.System.getProperties().getProperty("user.dir").contains(fs.getMount())) {
                long free = fs.getUsableSpace();
                long total = fs.getTotalSpace();
                long used = total - free;
                Disk sysFile = new Disk();
                sysFile.setDirName(fs.getMount());
                sysFile.setSysTypeName(fs.getType());
                sysFile.setTypeName(fs.getName());
                sysFile.setTotal(convertFileSize(total));
                sysFile.setFree(convertFileSize(free));
                sysFile.setUsed(convertFileSize(used));
                sysFile.setUsage(NumberUtil.round(NumberUtil.mul(used, total, 4), 100).doubleValue());
                disk = sysFile;
            }
        }
    }

    public String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }
}
