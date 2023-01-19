package kg.bekzhan.megalab.controllers;

import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/api/admin")
public class EditorController {
    private final RoleService roleService;
    private final NewsTagService newsTagService;

    //creating new role
    @PostMapping("/roles/{role}")
    @ApiOperation(value = "Creating a new role", notes = "Only the users with editor role can perform this operation.")
    public Role createRole(@PathVariable("role") String role) {
        return roleService.createRole(role);
    }

    //creating new newstag
    @PostMapping("/tags/{tag}")
    @ApiOperation(value = "Creating a new tag", notes = "Only the users with editor role can perform this operation.")
    public NewsTag createNewsTag(@PathVariable("tag") String newsTag) {
        return newsTagService.createTag(newsTag);
    }
}
