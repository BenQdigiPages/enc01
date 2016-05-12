//<script src="assets/dist/jquery.md5.js"></script>
//https://github.com/emn178/js-sha256

var enc01 = {
    var token = "";
    // Application Constructor
    initialize: function(_token) {
        token = _token;
    },
    decode: function(filepath,src) {
        /*java
        byte [] ret= new byte[src.length];
        byte key[]=getRealKey(downloadToken,filepath);
        int loc = 0;
        while (loc < src.length)
        {
            encRange(src,loc,key.length,ret, loc, key);
            loc +=key.length;
        }
        */
        var ret = new Uint8Array(src);
        var key = getRealKey(token,filepath);
        var loc = 0;
        while (loc < src.length)
        {
            encRange(src,loc,key.length,ret, loc, key);
            loc += key.length;
        }
        return ret;
    },
    getRealKey: function(filePath) {
        /*java
        byte [] realKey = null;
        int offset = calcOffset(filePath);
        String newSeed = DownloadToken.substring(0,offset) + filePath + DownloadToken.substring(offset);
        MessageDigest md  = MessageDigest.getInstance("SHA-256");
        md.update(newSeed.getBytes("UTF-8"));
        realKey = md.digest();
        return realKey;
        */
        var realKey = [];
        int offset = calcOffset(filePath);
        var newSeed = token.substring(0,offset) + filePath + token.substring(offset);
        realKey = sha256(newSeed);//https://github.com/emn178/js-sha256
        return realKey;
    },
    calcOffset: function(filePath) {
        /*java
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(filePath.getBytes("UTF-8"));
        byte [] x= md.digest();

        int sum = 0;
        for (int i=0; i< x.length;i+=2)
        {

            byte tmp[] = new byte[2];
            System.arraycopy(x, i, tmp, 0, 2);
            int value = byteArrayToSmallInt(tmp);
            sum+=value;
        }
        int offset = sum%64;

        return offset;
        */
        var x = $.md5(filePath.getBytes("UTF-8"));
        var sum = 0;
        for (int i=0; i< x.length;i+=2)
        {
            var temp = new Uint8Array[x[i]];
            int value = byteArrayToSmallInt(tmp);
            sum+=value;
        }
        int offset = sum%64;
        return offset;
    },
    byteArrayToSmallInt: function(b)
    {
        /*java
        return   b[1] & 0xFF |
                (b[0] & 0xFF) << 8 ;
        */
        return b[1] & 0xFF | (b[0] & 0xFF) << 8;
    }
};
