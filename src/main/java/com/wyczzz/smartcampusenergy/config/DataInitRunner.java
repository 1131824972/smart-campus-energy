package com.wyczzz.smartcampusenergy.config;

import com.wyczzz.smartcampusenergy.entity.Building;
import com.wyczzz.smartcampusenergy.entity.Device;
import com.wyczzz.smartcampusenergy.service.BuildingService;
import com.wyczzz.smartcampusenergy.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目启动后的初始化任务
 * 对应项目要求：需初始化的虚拟设备列表(Reference Table) [cite: 55]
 */
@Component // 标记为组件，启动时会自动扫描并执行
public class DataInitRunner implements CommandLineRunner {

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private DeviceService deviceService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("========== 正在检查数据初始化 ==========");

        // 1. 检查建筑表是否为空，如果为空则初始化
        if (buildingService.findAll().isEmpty()) {
            System.out.println(">>> 数据库为空，开始初始化建筑和设备数据...");
            initBuildingsAndDevices();
            System.out.println(">>> ✅ 数据初始化完成！");
        } else {
            System.out.println(">>> 数据库已有数据，跳过初始化。");
        }
    }

    private void initBuildingsAndDevices() {
        // --- 1. 创建建筑 ---
        // 建筑1：楸苑宿舍三号楼
        Building b1 = new Building();
        b1.setName("楸苑宿舍三号楼");
        b1.setLocation("Zone-QIU");
        b1.setFloors(6);
        b1.setPurpose("宿舍");

        // 建筑2：力行楼
        Building b2 = new Building();
        b2.setName("力行楼");
        b2.setLocation("Zone-LIXING");
        b2.setFloors(5);
        b2.setPurpose("教学");

        // 先保存建筑，这样它们才有ID，后面设备才能绑定
        // 这里我们简单处理，利用List引用
        List<Building> buildings = new ArrayList<>();
        buildings.add(b1);
        buildings.add(b2);
        buildingService.initData(buildings); // 保存到数据库

        // 重新查出来，确保拿到数据库生成的ID
        List<Building> savedBuildings = buildingService.findAll();
        Building savedB1 = savedBuildings.get(0); // 楸苑
        Building savedB2 = savedBuildings.get(1); // 力行楼

        // --- 2. 创建设备 (参考项目文档的表格) ---
        List<Device> devices = new ArrayList<>();

        // 楸苑的设备 (3个)
        devices.add(createDevice("宿舍智能电表-01", "METER_QIU_301", "301", 1000.0, savedB1));
        devices.add(createDevice("宿舍智能电表-02", "METER_QIU_302", "302", 1000.0, savedB1));
        devices.add(createDevice("宿舍智能电表-03", "METER_QIU_303", "303", 1000.0, savedB1));

        // 力行楼的设备 (2个)
        devices.add(createDevice("教室智能电表-01", "METER_LIXING_101", "101", 3500.0, savedB2));
        devices.add(createDevice("教室智能电表-02", "METER_LIXING_102", "102", 3500.0, savedB2));

        // ... 你可以继续按照文档表格补充更多设备，凑够10个 ...

        // 批量保存设备
        deviceService.initData(devices);
    }

    // 辅助方法：快速创建设备对象
    private Device createDevice(String name, String sn, String room, Double maxPower, Building building) {
        Device d = new Device();
        d.setName(name);
        d.setSn(sn);
        d.setRoomNumber(room);
        d.setMaxPower(maxPower);
        d.setBuilding(building);
        d.setStatus("ONLINE"); // 默认在线
        return d;
    }
}