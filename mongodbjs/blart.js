db.tweets_blart.find().forEach(function(x){ x.timestamp_ms = new NumberLong(x.timestamp_ms); db.tweets_blart.save(x)})

var begin = 1429056000000;
var inc = 86400000;
for(var count = 0; count < 7; count++){

var timestamp = {};
timestamp.$gte = begin;
timestamp.$lt = begin + inc;
print(JSON.stringify(timestamp))
var r = db.tweets_blart.find({timestamp_ms:timestamp}).count();
print(r)
begin += inc;
}
