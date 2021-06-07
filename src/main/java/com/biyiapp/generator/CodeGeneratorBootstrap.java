package com.biyiapp.generator;


/**
* @Description: mybatis-plus代码生成器
*/
public class CodeGeneratorBootstrap {
    //创建者
    static String author = "chenfeixiang";
    //表名数组
    static String[] tables = {"nj_camera","nj_device","nj_device_to_camera"};
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

        //目标controller所在的模块名
        String controllerModuleName = "nj";
//        String controllerModuleName = "admin";
//        String controllerModuleName = "portal";
//        String controllerModuleName = "sio";
        //目标service所在的模块名
        String serviceModuleName = "nj";
        //目标dap所在的模块名
        String repoModuleName = "nj";

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
