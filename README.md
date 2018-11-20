Octo-Stalker
============

Bypass Mobile Android exercise

### Application Specification

+ Display a list of user names and profile images that belong to the bypass organization.
  * Clicking on a member will display a list of user names and profile images of who they follow alphabetically.
  * This process repeats itself for the newly displayed user.
  * Searching should show a filtered list of users.
+ The cache library provided needs expiration and invalidation.
+ Provide tests in your solution.

Note #1: The code provided is out of date.  Please feel free to discard any and all of it and make use of any libraries and patterns you'd like.

Note #2: If you start receiving 403 errors while developing it's becuase Github has a request limit of 60 queries per ip address per hour.

### [Mockups](./Mockups.pdf)

### Provided for you
- Models
- Image Loading
- Restclient

### Example

#### HTTP Client Example

```java
getEndpoint().getOrganizationMember("bypasslane", new Callback<List<User>>() {
    @Override
    public void success(List<User> users, Response response) {}

    @Override
    public void failure(RetrofitError error) {}
});
```
#### Load Image Example
```java
ImageLoader.createImageLoader(this)
 .load("http://.../name.jpeg")
 .into(image);
```
