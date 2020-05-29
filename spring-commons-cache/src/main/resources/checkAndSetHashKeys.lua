----------------- Functions
local setHashFromList =
function (key, list)
    local result = {}
    local hkey
    local hvalue
    for index, element in ipairs(list) do
        if index % 2 == 0 then
            --Indice par
            hvalue = element

            local hexists = redis.call("HEXISTS", key, hkey)
            redis.call("PUBLISH", "log", tostring(hexists))
            redis.log(redis.LOG_NOTICE, tostring(hexists))
            if hexists == 0
            then
                redis.call("PUBLISH", "log", "Seteando hkey: " .. hkey .. " con value " .. hvalue)
                redis.log(redis.LOG_NOTICE, "Seteando hkey: " .. hkey .. " con value " .. hvalue)
                redis.call("HSET", key, hkey, hvalue)
                result[#result + 1] = hkey
                result[#result + 1] = hvalue
            else
                redis.call("PUBLISH", "log", "Ya existe hkey: " .. hkey .. " en key " .. key)
                redis.log(redis.LOG_NOTICE, "Ya existe hkey: " .. hkey .. " en key " .. key)
            end
        else
            --Indice impar
            hkey = element
        end
    end
    return result
end

----------------- Script
local key = KEYS[1]
local returnValue = {}

redis.call("PUBLISH", "log", "Ejecutando checkAndSetHashKeys sobre key: " .. key)
redis.log(redis.LOG_NOTICE, "Ejecutando checkAndSetHashKeys sobre key: " .. key)


redis.call("PUBLISH", "log", "Seteando valores")
redis.log(redis.LOG_NOTICE, "Seteando valores")
returnValue = setHashFromList(key, ARGV)

redis.call("PUBLISH", "log", "Fin checkAndSetHashKeys sobre key: " .. key)
redis.log(redis.LOG_NOTICE, "Fin checkAndSetHashKeys sobre key: " .. key)
return returnValue