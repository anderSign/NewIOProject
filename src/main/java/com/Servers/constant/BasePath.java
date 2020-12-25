package com.Servers.constant;

public enum BasePath {
    //\几个常用位置的路径名
    ClassPath("classPath:"),ResourcesPath("resources:"),TestClassPath("testClassPath:"),TestResourcePath("testResources:");
    private String basePath;

    public String getBasePath() {
        return basePath;
    }

    BasePath(String info) {
        basePath=info;
    }
    /*  测试的内容部分
        BasePath[] basePaths;
        basePaths=BasePath.values();
        for (BasePath basePath : basePaths) {
            System.out.println(basePath+":"+basePath.getBasePath());
        }
     */
}