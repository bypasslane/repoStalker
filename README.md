Octo-Stalker
============

Bypass Mobile Android exercise 

###Application Specification

+ Display a list of user names and profile images that belong to the bypass organization.
  * Clicking on a member will display who they follow. 
  * This process repeats itself for the newly displayed user

Feel free to improve on any code provided.

###Provided for you 
- Models 
- Image Loading
- Restclient

### Example

####HTTP Client Example

```java
getEndpoint().getOrganizationMember("bypasslane", new Callback<List<User>>() {
    @Override
    public void success(List<User> users, Response response) {}

    @Override
    public void failure(RetrofitError error) {}
});
```
####Load Image Example
```java
ImageLoader.createImageLoader(this)
 .load("http://.../name.jpeg")
 .into(image);
```
