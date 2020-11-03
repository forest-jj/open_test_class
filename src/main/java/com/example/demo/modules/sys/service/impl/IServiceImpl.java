package com.example.demo.modules.sys.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.example.demo.common.annotation.EndTime;
import com.example.demo.common.annotation.GreaterThan;
import com.example.demo.common.annotation.StartTime;
import com.example.demo.modules.sys.domain.SysStudent;
import com.example.demo.modules.sys.domain.SysUser;
import com.example.demo.modules.sys.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 条件查询
 */
@Slf4j
@Service
public class IServiceImpl implements IService {

    @Override
    public List<?> findByCondition(JpaSpecificationExecutor jpaSpecificationExecutor, Object entity) {
        return jpaSpecificationExecutor.findAll(this.getWhereClause(entity), Sort.by(Sort.Direction.DESC, "createTime"));
    }


    private Specification getWhereClause(Object entity) {
        return new SimpleSpecification(entity);
    }

    class SimpleSpecification implements Specification<Object> {
        private Predicate predicate = null;
        private CriteriaBuilder criteriaBuilder;
        private Object entity;

        public SimpleSpecification(Object entity) {
            this.entity = entity;
        }

        @Override
        public Predicate toPredicate(Root<Object> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            this.criteriaBuilder = criteriaBuilder;
            this.generatePredicate(this.entity, root);

            return this.predicate;
        }

        private void andPredicate(Predicate predicate) {
            if (predicate != null) {
                if (this.predicate == null) {
                    this.predicate = predicate;
                } else {
                    this.predicate = this.criteriaBuilder.and(this.predicate, predicate);
                }
            }
        }

        /**
         * TODO：其他类型查询
         * 自定义条件查询
         *
         * @param entity
         * @param root
         */
        private void generatePredicate(Object entity, From<Object, ?> root) {
            //反射字段 按字段查询
            Field[] fields = entity.getClass().getDeclaredFields();

            try {
                for (Field field : fields) {
                    String name = field.getName();
                    field.setAccessible(true);
                    Object value = field.get(entity);

                    if (ObjectUtil.isNotNull(value)) {
                        boolean greateThan = false;
                        boolean start = false;
                        boolean end = false;

                        //存在注解 跳过查询
                        if (field.getAnnotation(Version.class) != null) {
                            continue;
                        }

                        if (field.getAnnotation(GreaterThan.class) != null) {
                            greateThan = true;
                        }

                        if (field.getAnnotation(StartTime.class) != null) {
                            start = true;
                        }

                        if (field.getAnnotation(EndTime.class) != null) {
                            end = true;
                        }


                        if (value instanceof String) {//判断是否string类型
                            if (!StringUtils.isEmpty(value)) {
                                String stringValue = (String) value;
                                this.andPredicate(criteriaBuilder.like(root.get(name).as(String.class), "%" + stringValue + "%"));
                            }
                        } else if (value instanceof Integer) {
                            Integer intValue = (Integer) value;
                            if (greateThan) { //判断是否存在大于的注解
                                this.andPredicate(criteriaBuilder.greaterThan(root.get(name).as(Integer.class), intValue));
                            } else {
                                this.andPredicate(criteriaBuilder.equal(root.get(name).as(Integer.class), intValue));
                            }
                        } else if (value instanceof Collection) {
                            Collection<SysStudent> collection = (Collection<SysStudent>) value;
                            if (collection.size() > 0) {
                                Iterator<SysStudent> iterator = collection.iterator();
                                CriteriaBuilder.In<Integer> in = criteriaBuilder.in(root.get("classesId").as(Integer.class));
                                while (iterator.hasNext()) {
                                    SysStudent s = iterator.next();
                                    in.value(s.getSysClass().getClassesId());
                                }

                                this.andPredicate(criteriaBuilder.not(in));
                            }

                        } else if (value instanceof LocalDateTime){
                            LocalDateTime time = (LocalDateTime) value;
                            if (start)
                                this.andPredicate(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(LocalDateTime.class), time));
                            else if (end)
                                this.andPredicate(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(LocalDateTime.class),time));

                        }else if (value instanceof SysUser) {
                            SysUser sysUser = (SysUser) value;
                            if (sysUser.getUserId() == null)
                                continue;
                            Join join = root.join("sysStudent", JoinType.LEFT);

                            this.andPredicate(criteriaBuilder.equal(join.get("sysUser").as(SysUser.class), sysUser));
                        } else {
                            log.error("暂只支持到Interger,String类型");
                        }
                    } else {
                        log.error("不支持的数据类型", name, field);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
