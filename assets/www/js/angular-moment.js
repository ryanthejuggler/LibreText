angular.module('fromNow',[]).filter('fromNow', function() {
  return function(date) {
    return moment(date).fromNow();
  }
});
