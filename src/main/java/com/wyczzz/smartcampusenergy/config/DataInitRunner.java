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
 * 对应项目要求：需初始化的虚拟设备列表(Reference Table)
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
            System.out.println(">>> ✅ 数据初始化完成！已生成4栋建筑和10个设备。");
        } else {
            System.out.println(">>> 数据库已有数据，跳过初始化。");
        }
    }

    private void initBuildingsAndDevices() {
        // --- 1. 创建建筑 (根据文档表中的"所属建筑"列，我们需要4栋楼) ---
        List<Building> buildings = new ArrayList<>();

        // 建筑1：楸苑宿舍三号楼 [cite: 57]
        Building b1 = new Building();
        b1.setName("楸苑宿舍三号楼");
        b1.setLocation("Zone-QIU");
        b1.setFloors(6);
        b1.setPurpose("宿舍");
        buildings.add(b1);

        // 建筑2：力行楼 [cite: 57]
        Building b2 = new Building();
        b2.setName("力行楼");
        b2.setLocation("Zone-LIXING");
        b2.setFloors(5);
        b2.setPurpose("教学");
        buildings.add(b2);

        // 建筑3：软件学院楼 [cite: 57]
        Building b3 = new Building();
        b3.setName("软件学院楼");
        b3.setLocation("Zone-SOFT");
        b3.setFloors(5);
        b3.setPurpose("科研/办公");
        buildings.add(b3);

        // 建筑4：图书馆 [cite: 57]
        Building b4 = new Building();
        b4.setName("图书馆");
        b4.setLocation("Zone-LIB");
        b4.setFloors(4);
        b4.setPurpose("公共服务");
        buildings.add(b4);

        // 保存建筑到数据库，让它们生成ID
        buildingService.initData(buildings);

        // 重新查出来，确保拿到数据库生成的ID（因为是按顺序存的，按顺序取即可）
        List<Building> savedBuildings = buildingService.findAll();
        // 为了保险起见，我们通过名字匹配一下对象，防止顺序乱了
        Building savedB1_Qiu = findBuildingByName(savedBuildings, "楸苑宿舍三号楼");
        Building savedB2_Lixing = findBuildingByName(savedBuildings, "力行楼");
        Building savedB3_Soft = findBuildingByName(savedBuildings, "软件学院楼");
        Building savedB4_Lib = findBuildingByName(savedBuildings, "图书馆");

        // --- 2. 创建设备 (严格按照文档表格 [cite: 57]) ---
        List<Device> devices = new ArrayList<>();

        // 序号1: 宿舍智能电表-01
        devices.add(createDevice("宿舍智能电表-01", "METER_QIU_301", "301", 1000.0, savedB1_Qiu));
        // 序号2: 宿舍智能电表-02
        devices.add(createDevice("宿舍智能电表-02", "METER_QIU_302", "302", 1000.0, savedB1_Qiu));
        // 序号3: 宿舍智能电表-03
        devices.add(createDevice("宿舍智能电表-03", "METER_QIU_303", "303", 1000.0, savedB1_Qiu));

        // 序号4: 教室智能电表-01
        devices.add(createDevice("教室智能电表-01", "METER_LIXING_101", "101", 3500.0, savedB2_Lixing));
        // 序号5: 教室智能电表-02
        devices.add(createDevice("教室智能电表-02", "METER_LIXING_102", "102", 3500.0, savedB2_Lixing));
        // 序号6: 阶梯教室主控表
        devices.add(createDevice("阶梯教室主控表", "METER_LIXING_205", "205", 7000.0, savedB2_Lixing));

        // 序号7: 实验室专用电表
        devices.add(createDevice("实验室专用电表", "METER_SOFT_LAB1", "306", 12000.0, savedB3_Soft));
        // 序号8: 办公室电表
        devices.add(createDevice("办公室电表", "METER_SOFT_OFFICE", "402", 4000.0, savedB3_Soft));

        // 序号9: 公共区域电表
        devices.add(createDevice("公共区域电表", "METER_LIB_HALL", "一楼大厅", 6000.0, savedB4_Lib));
        // 序号10: 阅览室电表
        devices.add(createDevice("阅览室电表", "METER_LIB_READ", "三楼阅览室", 3000.0, savedB4_Lib));

        // 批量保存设备
        deviceService.initData(devices);
    }

    // 辅助方法：根据名字从列表里找建筑
    private Building findBuildingByName(List<Building> list, String name) {
        return list.stream()
                .filter(b -> b.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("初始化失败：找不到建筑 " + name));
    }

    // 辅助方法：快速创建设备对象
    private Device createDevice(String name, String sn, String room, Double maxPower, Building building) {
        Device d = new Device();
        d.setName(name);
        d.setSn(sn);
        d.setRoomNumber(room);
        d.setMaxPower(maxPower);
        d.setBuilding(building);
        d.setStatus("ONLINE"); // 默认在线 [cite: 15]
        return d;
    }
}