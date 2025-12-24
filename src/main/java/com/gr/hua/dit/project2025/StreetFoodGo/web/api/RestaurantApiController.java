package com.gr.hua.dit.project2025.StreetFoodGo.web.api;

import com.gr.hua.dit.project2025.StreetFoodGo.core.service.model.RestaurantDto;
import com.gr.hua.dit.project2025.StreetFoodGo.core.service.RestaurantService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@Tag(
        name = "Restaurants API",
        description = "REST API για αναζήτηση εστιατορίων στο StreetFoodGo"
)
public class RestaurantApiController {

    private final RestaurantService restaurantService;

    public RestaurantApiController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Operation(
            summary = "Αναζήτηση εστιατορίων",
            description = """
                            Επιστρέφει μία λίστα εστιατορίων.
                            Υποστηρίζει προαιρετικά φίλτρα περιοχής και κουζίνας.
                          """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Επιτυχής επιστροφή της λίστας εστιατορίων"
            )
    })
    @GetMapping
    public List<RestaurantDto> getRestaurants(

            @Parameter(
                    description = "Περιοχή στην οποία βρίσκεται το εστιατόριο",
                    example = "Θεσσαλονίκη"
            )
            @RequestParam(required = false)
            String area,

            @Parameter(
                    description = "Τύπος κουζίνας του εστιατορίου",
                    example = "Ιταλικό"
            )
            @RequestParam(required = false)
            String cuisine
    ) {
        return restaurantService.findRestaurants(area, cuisine);
    }
}