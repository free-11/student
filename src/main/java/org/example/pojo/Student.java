package org.example.pojo;

// 1. 新增这两个必要的导入（必须加，否则注解会报错）
import com.fasterxml.jackson.annotation.JsonProperty;
// 如果你不需要 JsonIgnoreProperties 可以只保留上面这一个导入
// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class Student {

    private Integer studentId;
    private String name;
    private String phone;
    private Integer age;
    private String gender;

    // 2. 替换原来的 @JsonIgnore 为 @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    // 作用：新增时能接收前端的 addressId，查询返回时隐藏该字段
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer addressId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer collegeId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer roleId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer majorId;

    // 以下关联表展示字段保留不变
    private String province;
    private String city;
    private String district;
    private String detail;
    private String collegeName;
    private String roleName;
    private String majorName;
}