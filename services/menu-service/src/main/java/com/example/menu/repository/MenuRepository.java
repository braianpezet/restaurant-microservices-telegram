package com.example.menu.repository;

import com.example.menu.entities.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu,Long> {

}
