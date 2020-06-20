----------------- Functions
local setHashFromList =
    function (key, list)
        local hkey
        local hvalue
        for index, element in ipairs(list) do
            if index % 2 == 0 then
                --Indice par
                hvalue = element
                redis.call("PUBLISH", "log", "Seteando hkey: " .. hkey .. " con value " .. hvalue)
                redis.log(redis.LOG_NOTICE, "Seteando hkey: " .. hkey .. " con value " .. hvalue)
                redis.call("HSET", key, hkey, hvalue)
            else
                --Indice impar
                hkey = element
            end
        end
    end

----------------- Script
local key = KEYS[1]
local returnValue = {}

redis.call("PUBLISH", "log", "Ejecutando checkAndSetHash sobre key: " .. key)
redis.log(redis.LOG_NOTICE, "Ejecutando checkAndSetHash sobre key: " .. key)

if redis.call("EXISTS", key) == 0
then
    redis.call("PUBLISH", "log", "La key no existe, seteando valores")
    redis.log(redis.LOG_NOTICE, "La key no existe, seteando valores")
    setHashFromList(key, ARGV)
    returnValue = ARGV
else
    redis.call("PUBLISH", "log", "La key ya existe")
    redis.log(redis.LOG_NOTICE, "La key ya existe")
end

redis.call("PUBLISH", "log", "Fin checkAndSetHash sobre key: " .. key)
redis.log(redis.LOG_NOTICE, "Fin checkAndSetHash sobre key: " .. key)
return returnValue