## 访问地址（固定地址）
http://localhost:8110/swagger-ui.html
## 配置
@Configuration
@EnableSwagger2
Swagger2Config
## 注解
例如：
实体类  IntegralGrade
@ApiModel(value="IntegralGrade对象", description="积分等级表")

属性  private Long id
@ApiModelProperty(value = "编号", example = "1")

控制器  AdminIntegralGradeController
@Api(tags = "积分等级管理")

接口方法
@ApiOperation(value = "获取hello world")

@ApiOperation(value = "根据id删除数据记录", notes = "逻辑删除")
    @DeleteMapping("/remove/{id}")
    public boolean removeById(@PathVariable Long id){
        return integralGradeService.removeById(id);
    }
    
参数注解    
@ApiParam(value = "要删除的id", example = "1", required = true)
