package kg.bekzhan.megalab.controllers;

import kg.bekzhan.megalab.entities.NewsTag;
import kg.bekzhan.megalab.entities.Role;
import kg.bekzhan.megalab.services.NewsTagService;
import kg.bekzhan.megalab.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final RoleService roleService;
    private final NewsTagService newsTagService;

    //creating new role
    @PostMapping("/roles/{role}")
    public Role createRole(@PathVariable("role") String role) {
        return roleService.createRole(role);
    }

    //creating new newstag
    @PostMapping("/tags/{tag}")
    public NewsTag createNewsTag(@PathVariable("tag") String newsTag) {
        return newsTagService.createTag(newsTag);
    }
}
