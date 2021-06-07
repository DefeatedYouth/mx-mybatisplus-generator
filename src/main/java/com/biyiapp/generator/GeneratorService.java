package com.biyiapp.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/**
* @Description: mybatis-plus代码生成器实现类
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneratorService {

    /**
     * jdbc
     */
    private static final String jdbcUrl = "jdbc:mysql://rm-bp16r7tg01u8lnytg4o.mysql.rds.aliyuncs.com:3306/nj_overallview?useUnicode=true&characterEncoding=utf-8&useSSL=false";

    /**
     * jdbc 用户名
     */
    private static final String username ="nj_overallview";

    /**
     * jdbc 密码
     */
    private static final String password = "uWfzgwyXvJciS1db";

    /**
     * jdbc 驱动
     */
    public static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    /**
     * base类所在包名
     */
    private static final  String baseClassPackage = "cn.exrick.xboot.base";

    /**
     * 生成的mapper类后缀
     */
    public static final String MAPPER_NAME = "%sDao";//%s会自动填充表实体属性

    /**
     * 路径分隔符
     */
    @Builder.Default
    public String PATH_SEPARATOR = "\\";

    /**
     * 是否是mac操作系统，默认false，即windows
     */
    @Builder.Default
    public boolean mac = false;

    /**
     * 创建者
     */
    @Builder.Default
    public  String AUTHOR = "default";

    /**
     * 根包名
     */
    private String rootPackageName;

    /**
     * controller类所在的模块名
     */
    private String controllerModuleName;

    /**
     * service类所在的模块名
     */
    private String serviceModuleName;

    /**
     * repo类所在的模块名
     */
    private String repoModuleName;

    /**
     * 模板配置
     */
    @Builder.Default
    private TemplateConfig templateConfig = new TemplateConfig();

    /**
     * controller模块所在main目录
     */
    private String controllerProjectMainPath;

    /**
     * service模块所在main目录
     */
    private String serviceProjectMainPath;

    /**
     * repo模块所在main目录
     */
    private String repoProjectMainPath;

    /**
     * 实体查询是否生成，默认true
     */
    @Builder.Default
    private boolean outPutEntityQuery = true;

    /**
     * 实体生成目录
     */
    @Builder.Default
    private String entityQueryPackage = "query";


    public void generateByTables(String... tableNames) {
        if (mac) {
            PATH_SEPARATOR = "/";
        }
        String controllerRootCodePath = controllerProjectMainPath.concat(PATH_SEPARATOR + "java" + PATH_SEPARATOR);
        String serviceRootCodePath = serviceProjectMainPath.concat(PATH_SEPARATOR + "java" + PATH_SEPARATOR);
        String repoRootCodePath = repoProjectMainPath.concat(PATH_SEPARATOR + "java" + PATH_SEPARATOR);
        //mapper文件路径
        String repoRootMapperPath = repoProjectMainPath.concat(PATH_SEPARATOR + "resources" + PATH_SEPARATOR + "mappers" + PATH_SEPARATOR);

        //全局配置
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(false)
                .setEnableCache(false)//XML 二级缓存
                .setMapperName(MAPPER_NAME)//mapper类后缀
                .setAuthor(AUTHOR)//作者
                .setOutputDir(controllerRootCodePath)//输出代码路径
                .setFileOverride(false)//已存在文件替换
                .setBaseColumnList(true) //XML columList
                .setSwagger2(true)//是否开启Swagger2
                .setBaseResultMap(true)//XML ResultMap
                .setServiceName("%sService")
                .setOpen(false)//是否打开输出目录
                .setDateType(DateType.ONLY_DATE)//Date格式,默认LocalDateTime
                ;

        //数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig
                .setUrl(jdbcUrl)
                .setUsername(username)
                .setPassword(password)
                .setDriverName(DRIVER_NAME);

        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig();

        strategyConfig
                .setCapitalMode(true)// 全局大写命名
                .setEntityLombokModel(true)//lombok开启
                .setEntityBuilderModel(true)
                .setColumnNaming(NamingStrategy.underline_to_camel)//字段映射 驼峰
                .setNaming(NamingStrategy.underline_to_camel)//表映射 驼峰命名
                .setRestControllerStyle(true)//开启rest
                .setLogicDeleteFieldName("is_delete")//逻辑删除字段
                .setInclude(tableNames)//需要生成的表
                .setEntitySerialVersionUID(false)//关闭序列化UID
                ;

        if (baseClassPackage != null) {
            strategyConfig
//                    .setSuperControllerClass(baseClassPackage.concat(".BaseController"))
//                    .setSuperEntityClass(baseClassPackage.concat(".entity").concat(".StmBaseEntity"))
                    .setSuperEntityClass(baseClassPackage.concat(".StmBaseEntity"))
//                    .setSuperMapperClass(baseClassPackage.concat(".dao").concat(".BaseDao"))
//                    .setSuperServiceClass(baseClassPackage.concat(".BaseService"))
                    .setSuperEntityColumns("id", "is_delete","create_time");
//                    .setSuperServiceImplClass(baseClassPackage.concat(".BaseServiceImpl")
//                    );
        }

        Map<String, Object> map = new HashMap<>(5);
        map.put("BaseClassPackage", baseClassPackage);
        //自定义属性，可在模板中使用
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                this.setMap(map);
            }
        };

        //模块类型【0 controller层;1 service层 2 repo层】
        int type = 0;
        if (Objects.nonNull(templateConfig.getController())) {
            handel(type,serviceRootCodePath, repoRootCodePath, config, dataSourceConfig, strategyConfig, map, cfg,templateConfig,null);
        }
        if (Objects.nonNull(templateConfig.getService()) || Objects.nonNull(templateConfig.getServiceImpl())) {
            type = 1;
            handel(type,serviceRootCodePath, repoRootCodePath, config, dataSourceConfig, strategyConfig, map, cfg,templateConfig,null);
        }
        if (Objects.nonNull(templateConfig.getMapper()) ||Objects.nonNull(templateConfig.getEntity(false))||Objects.nonNull(templateConfig.getXml()) || outPutEntityQuery) {
            type = 2;
            handel(type,serviceRootCodePath, repoRootCodePath, config, dataSourceConfig, strategyConfig, map, cfg,templateConfig,repoRootMapperPath);
        }
    }

    private void handel(int type, String serviceRootCodePath, String repoRootCodePath, GlobalConfig config, DataSourceConfig dataSourceConfig, StrategyConfig strategyConfig, Map<String, Object> map, InjectionConfig cfg, TemplateConfig templateConfig, String repoRootMapperPath) {
        //自定义属性,模板中使用
        map.put("RootPackage", rootPackageName);
        map.put("ServiceLocation", rootPackageName.concat(".").concat(serviceModuleName).concat(".").concat("service"));
        map.put("EntityLocation", rootPackageName.concat(".").concat(repoModuleName).concat(".").concat("entity"));
        map.put("MapperLocation", rootPackageName.concat(".").concat(repoModuleName).concat(".").concat("dao"));
        map.put("QueryLocation", rootPackageName.concat(".").concat(repoModuleName).concat(".").concat("query"));

        TemplateConfig templateConfigTemp = new TemplateConfig();
        templateConfigTemp.setEntityKt(null);
        templateConfigTemp.setController(templateConfig.getController());
        templateConfigTemp.setService(templateConfig.getService());
        templateConfigTemp.setServiceImpl(templateConfig.getServiceImpl());
        templateConfigTemp.setEntity(templateConfig.getEntity(false));
        templateConfigTemp.setMapper(templateConfig.getMapper());
        templateConfigTemp.setXml(templateConfig.getXml());

        AutoGenerator autoGenerator = new AutoGenerator();
        List<FileOutConfig> focList = new ArrayList<>();

        //生成自定义查询对象
        if (outPutEntityQuery) {
            String queryFullPackage = rootPackageName.concat(".").concat(repoModuleName).concat(".").concat(entityQueryPackage);
            map.put("EntityQueryPackage", entityQueryPackage);
            String outPutPath = repoRootCodePath.concat(queryFullPackage.replace(".", PATH_SEPARATOR)).concat(PATH_SEPARATOR);
            focList.add(new FileOutConfig("/templates/entityQuery.java.vm") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return outPutPath.concat(tableInfo.getEntityName()).concat("Query.java");
                }
            });
        }
        cfg.setFileOutConfigList(focList);
        autoGenerator.setCfg(cfg);

        switch (type) {
            case 0:
                //仅生成controller
                templateConfigTemp.setService(null);
                templateConfigTemp.setServiceImpl(null);
                templateConfigTemp.setEntity(null);
                templateConfigTemp.setMapper(null);
                templateConfigTemp.setXml(null);
                autoGenerator.setTemplate(templateConfigTemp);

                autoGenerator.setGlobalConfig(config)
                        .setDataSource(dataSourceConfig)
                        .setStrategy(strategyConfig)
                        .setPackageInfo(new PackageConfig()
                                .setParent(rootPackageName)
                                .setModuleName(controllerModuleName)
                        ).execute();
                break;
            case 1:
                //仅生成service
                templateConfigTemp.setController(null);
                templateConfigTemp.setEntity(null);
                templateConfigTemp.setMapper(null);
                templateConfigTemp.setXml(null);
                autoGenerator.setTemplate(templateConfigTemp);
                config.setOutputDir(serviceRootCodePath);

                autoGenerator.setGlobalConfig(config)
                        .setDataSource(dataSourceConfig)
                        .setStrategy(strategyConfig)
                        .setPackageInfo(new PackageConfig()
                                .setParent(rootPackageName)
                                .setModuleName(serviceModuleName)
                                .setMapper("dao")
                        ).execute();
                break;
            case 2:
                //仅生成repo
                templateConfigTemp.setController(null);
                templateConfigTemp.setServiceImpl(null);
                templateConfigTemp.setService(null);
                autoGenerator.setTemplate(templateConfigTemp);
                config.setOutputDir(repoRootCodePath);
                map.put("RootPackage", rootPackageName.concat(".").concat(repoModuleName));

                if (templateConfigTemp.getXml() != null) {
                    //关闭默认的xml生成
                    templateConfigTemp.setXml(null);

                    //自定义xml生成目录
                    focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
                        @Override
                        public String outputFile(TableInfo tableInfo) {
                            return repoRootMapperPath.concat(tableInfo.getEntityName()).concat(".xml");
                        }
                    });
                }

                autoGenerator.setGlobalConfig(config)
                        .setDataSource(dataSourceConfig)
                        .setStrategy(strategyConfig)
                        .setPackageInfo(new PackageConfig()
                                .setParent(rootPackageName)
                                .setModuleName(repoModuleName)
                                .setEntity("entity")
                                .setMapper("dao")
                        ).execute();
                break;
            default:
                return ;
        }
    }

    public GeneratorService outPutXml(boolean value) {
        if (!value) {
            this.templateConfig.setXml(null);
        }
        return this;
    }

    public GeneratorService outPutServiceImpl(boolean value) {
        if (!value) {
            this.templateConfig.setServiceImpl(null);
        }
        return this;
    }

    public GeneratorService outPutService(boolean value) {
        if (!value) {
            this.templateConfig.setService(null);
        }
        return this;
    }

    public GeneratorService outPutEntity(boolean value) {
        if (!value) {
            this.templateConfig.setEntity(null);
        }
        return this;
    }

    public GeneratorService outPutMapper(boolean value) {
        if (!value) {
            this.templateConfig.setMapper(null);
        }
        return this;
    }

    public GeneratorService outPutController(boolean value) {
        if (!value) {
            this.templateConfig.setController(null);
        }
        return this;
    }

    public GeneratorService outPutEntityKt(boolean value) {
        if (!value) {
            this.templateConfig.setEntityKt(null);
        }
        return this;
    }

    public GeneratorService outPutEntityQuery(boolean value) {
        outPutEntityQuery = value;
        return this;
    }

}
