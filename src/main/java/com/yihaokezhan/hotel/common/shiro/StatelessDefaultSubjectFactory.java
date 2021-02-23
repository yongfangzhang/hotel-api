package com.yihaokezhan.hotel.common.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {
    /**
     * 通过调用 context.setSessionCreationEnabled(false) 表示不创建会话； 如果之后调用 Subject.getSession() 将抛出
     * DisabledSessionException 异常。
     */
    @Override
    public Subject createSubject(SubjectContext context) {
        // 不创建session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
