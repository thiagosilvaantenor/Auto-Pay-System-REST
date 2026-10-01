package com.my_projects.auto_pay_system_rest.infrastructure.persistence.repository;

import com.my_projects.auto_pay_system_rest.infrastructure.persistence.entity.PagamentoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface SpringDataPagamentoRepository extends JpaRepository<PagamentoJpaEntity, UUID> {
}
