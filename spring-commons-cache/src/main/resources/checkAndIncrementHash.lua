----------------- Functions
local incrementHashFromList =
    function (key, list)
        local hkey
        local hvalue
        for index, element in ipairs(list) do
            if index % 2 == 0 then
                --Indice par
                hvalue = element
                redis.call("PUBLISH", "log", "Incrementando hkey: " .. hkey .. " con value " .. hvalue)
                redis.log(redis.LOG_NOTICE, "Incrementando hkey: " .. hkey .. " con value " .. hvalue)
                redis.call("HINCRBYFLOAT", key, hkey, hvalue)
            else
                --Indice impar
                hkey = element
            end
        end
    end

----------------- Script
local key = KEYS[1]
local returnValue
redis.call("PUBLISH", "log", "Ejecutando checkAndIncrementHash sobre key: " .. key)
redis.log(redis.LOG_NOTICE, "Ejecutando checkAndIncrementHash sobre key: " .. key)

if redis.call("EXISTS", key) == 1
then
    redis.call("PUBLISH", "log", "La key existe, incrementando valores")
    redis.log(redis.LOG_NOTICE, "La key existe, incrementando valores")
    incrementHashFromList(key, ARGV)
    returnValue = redis.call("HGETALL", key)
else
    redis.call("PUBLISH", "log", "La key no existe")
    redis.log(redis.LOG_NOTICE, "La key no existe")
end

redis.call("PUBLISH", "log", "Fin checkAndIncrementHash sobre key: " .. key)
redis.log(redis.LOG_NOTICE, "Fin checkAndIncrementHash sobre key: " .. key)
return returnValue