package ua.alxmute.data.access.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.alxmute.data.access.domain.Route;

public interface RouteRepository extends JpaRepository<Route, Long> {
}
