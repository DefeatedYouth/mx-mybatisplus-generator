package com.biyiapp.generator;


import java.util.ArrayList;

/**
* @Description: mybatis-plus代码生成器
*/
public class CodeGeneratorBootstrap {
    //创建者
    static String author = "chenfeixiang";

    //controller模块所在main目录,替换成自己的,最后一级路径到main
    static  String controllerProjectMainPath="/Users/chenfeixiang/IdeaProjects/workspace2/nj_overallview_backend/src/main";
    //service模块所在main目录,替换成自己的,最后一级路径到main
    static  String serviceProjectMainPath="/Users/chenfeixiang/IdeaProjects/workspace2/nj_overallview_backend/src/main";
    //repo模块所在main目录,替换成自己的,最后一级路径到main
    static  String repoProjectMainPath="/Users/chenfeixiang/IdeaProjects/workspace2/nj_overallview_backend/src/main";

    //是否是mac操作系统,涉及路径分隔符
    static boolean mac = true;

    public static void main(String[] args) {

        //项目根包名
        String rootPackageName = "cn.exrick.xboot.modules";

        ArrayList<String> moduleList = new ArrayList<String>();
        ArrayList<String[]> tabGroupList = new ArrayList<String[]>();
        //第 1 组，表名数组
        String[] tables1 = {"axf_device"};
        tabGroupList.add(tables1);
        moduleList.add("anxiaofang");
        //第 2 组，表名数组
        String[] tables2 = {"base_camera","base_camera_records","base_device","base_device_to_camera","base_site","base_place"};
        tabGroupList.add(tables2);
        moduleList.add("base");
        //第 3 组，表名数组
        String[] tables3 = {"byq_alarm","byq_book","byq_danger","byq_defect","byq_fault","byq_fuhe","byq_overhaul","byq_realdata","byq_rungear","byq_test","byq_youshepu"};
        tabGroupList.add(tables3);
        moduleList.add("bianyaqi");
        //第 4 组，表名数组
        String[] tables4 = {"dlq_alarm","dlq_book","dlq_danger","dlq_defect","dlq_fault","dlq_feed","dlq_fuhe","dlq_overhaul","dlq_realdata","dlq_sfsix","dlq_test"};
        tabGroupList.add(tables4);
        moduleList.add("duanluqi");
        //第 5 组，表名数组
        String[] tables5 = {"hj_chushiji","hj_dengguang","hj_fengji","hj_kongtiao","hj_sfsex","hj_shidu","hj_shuibang","hj_shuisheng","hj_shuiwei","hj_wendu"};
        tabGroupList.add(tables5);
        moduleList.add("huanjing");
        //第 6 组，表名数组
        String[] tables6 = {"job_ops","job_repair","job_ticket"};
        tabGroupList.add(tables6);
        moduleList.add("job");
        //第 7 组，表名数组
        String[] tables7 = {"robot_alarm","robot_book","robot_insp_message","robot_realdata","robot_resume","robot_insp_records"};
        tabGroupList.add(tables7);
        moduleList.add("robot");
        for (int i = 0; i < tabGroupList.size(); i++) {
            String[] tables = tabGroupList.get(i);
            String moduleName = moduleList.get(i);
            //目标controller所在的模块名
            String controllerModuleName = moduleName;
            //目标service所在的模块名
            String serviceModuleName = moduleName;
            //目标dap所在的模块名
            String repoModuleName = moduleName;

            GeneratorService generatorService = GeneratorService.builder()
                    .AUTHOR(author)
                    .rootPackageName(rootPackageName)
                    .controllerModuleName(controllerModuleName)
                    .serviceModuleName(serviceModuleName)
                    .repoModuleName(repoModuleName)
                    .controllerProjectMainPath(controllerProjectMainPath)
                    .serviceProjectMainPath(serviceProjectMainPath)
                    .repoProjectMainPath(repoProjectMainPath)
                    .mac(mac)
                    .build();

            //默认均生成,只需要生成部分文件,可将其他置为false;
            //默认已存在该同名文件忽略,不替换老文件
            generatorService
//                .outPutController(false)
//                .outPutService(false)
//                .outPutServiceImpl(false)
//                .outPutEntity(false)
//                .outPutEntityQuery(false)
//                .outPutMapper(false)
                    .outPutXml(false)
                    .generateByTables(tables);
        }

    }
}
