package jp.co.sss.cytech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.cytech.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer>{

}
