### Basic workflow:
1. Use ```/api/store/searchArtists``` to search for artists and get their id's.
2. ```/api/user/saveFavouriteArtist``` to save an artist as favourite for a user with the id that was got from step 1.
3. ```/api/user/getFavouriteArtists``` to get favourite artists for a user
4. ```/api/store/searchAlbums``` to get the top albums for an artist by using the artists id from step 3.

### Caching:
Since itunes only allows to call them 20 times every minute, we are caching every successful request for 24hours.
