package cn.bugstack.domain.award.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AwardStateVO {

    create("create", "创建中"),
    complete("complete", "发奖完成"),
    fail("fail", "发奖失败")
    ;


    private final String code;
    private final String info;
}
