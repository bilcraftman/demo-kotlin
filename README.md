# Objective / Goal
Demonstrate how we can write same application with less code and less dependencies by using a Java friendly language and Java ecosystem.

- https://bf-gitlab/gitlab/DevCommunity/demo-kotlin
- http://wiki-it/display/DevCommunity/Demo+Kotlin

## Steps
 - Use existing spring boot REST app with DB, lombok and mapstruct.
 - This repo demonstrates how to do java without and with Vavr.
 - Add Kotlin support.
   - Discuss controllers.
   - Discuss repositories.
   - Discuss entities.
   - Discuss services.

## Conclusion
 - Third party libs replaced by language's capabilities.
 - Less libs:
    - Reduced attack surface.
    - Reduced risk of bugs.
 - Code simpler to read.
 - Resemblance with ReactJS with TypeScript or ES6 with Flow.
 - Capabilities of the Spring Boot framework are not impacted.
 - Mapping with language features studied right before. 

## Practice

Try to do one of these tasks:

 - Try to create an endpoint who is loading the robots inserted in last 30 seconds.
 - Add other resources next to Robots, like Cars, Games, Fruits.
   - __Bonus__: Try to code the complete feature in one single file.

## Resources
- [Spring boot doc - kotlin based app.](https://spring.io/guides/tutorials/spring-boot-kotlin/)
- [Kotlin lang docs - spring boot.](https://kotlinlang.org/docs/jvm-spring-boot-restful.html)
- [Josh Long - Bootiful Kotlin.](https://www.youtube.com/watch?v=IGt_T972pKE)

## Highlights

We can write kotlin in java style. No big harm will be done, and this will help us to switch to a modern language keeping LTS versions of JVM/JDK.

> This will reduce the impact and costs of adopting a new technology.

---

When creating a new object - we'll invoke the constructor directly, rather than using a keyword (_new_), which is more logical.

---
__Controllers__ are simpler now, we can map one endpoint to a service function who's in charge to do the business. 

``com.example.demo.api.RobotsController.getRobot``
```kotlin
@GetMapping("{id}")
fun getRobot(@PathVariable id: Long): RobotView = service.findById(id)
```
---
We can replace _"less"_ builders with basic constructors thanks to named parameters.

Having this regular value object:

``com.example.demo.api.ErrorBody``
````kotlin
internal class ErrorBody (
    var timestamp: Instant = Instant.now(),
    var status: Int = 500,
    var error: String? = "Internal Server Error",
    var message: String? = "",
    var path: String = ""
)
````
We can use it with named params or regular params. Example of using named params:

``com.example.demo.api.GenericExceptionHandler.handleConflict``
````kotlin
val details = ErrorBody(
       timestamp = Instant.now(),
       status = ex.httpCode,
       error = ex.message,
       message = ex.toString(),
       path = ((request as ServletWebRequest).request.requestURI)
 )
````
---
Have multiple DTOs in the same file in case we have related or simple ones. 
> The `.kt` file does not need to have a class with same name.

``com/example/demo/domain/DTOs.kt``
````kotlin
package com.example.demo.domain

class NewRobotRequest(
    var name: String,
   //...
)

class PatchRobotRequest(
    var name: String,
   //...
)

class RobotView(
    var id: Long? = null
    //...
)
````
---
Use __companion objects__ to create __factory methods__. Somewhat respecting the OOP principle of having objects who know how to _handle_ themselves.

``com/example/demo/domain/Robot.kt:21``
````kotlin
companion object {
  fun new(name: String, description: String) = 
      Robot(
         null,
         name,
         description,
         null,
         null,
         null
     )
 }
````
---
Use __null safe__ operator for fields who might have a null value so that compiler can check at _compile time_ the safe use of variable.  

``com/example/demo/domain/Robot.kt``

````kotlin
class Robot(
    var id: Long? = null,
    var name: String
) {
    companion object {
        fun new(name: String, description: String) = Robot(
            null,
            name
        )
    }
}
````
--- 
Use ``val`` for __final__ and ``var`` for regular variables.

---
Create lists with functions ``val list = mutableListOf<ExceptionDetail>()``

---

Simplify operations done around an object, like updating an entity, by using ``with() {..}``

``com.example.demo.domain.RobotsService.applyPatch``
````kotlin
fun applyPatch(robot: Robot, dto: PatchRobotRequest): Robot
{
  with(robot) {
      name = dto.name
      description = dto.description
      updated = Instant.now()
  }

  return robot
}
````