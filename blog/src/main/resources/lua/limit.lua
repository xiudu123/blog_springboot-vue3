--[[
lua脚本配合redis实现接口的限流
--]]

-- 获取参数
local key = KEYS[1] -- 限流key
local count = tonumber(ARGV[1]) -- 限流次数
local time = tonumber(ARGV[2]) -- 限流时间, 单位秒
local current = redis.call('get', key) -- 查看当前接口在单位限流时间内的访问了的次数


if current and tonumber(current) > count then -- 如果访问次数大于了限流次数
    return tonumber(current or "0")
end

current = redis.call('incr', key) -- 访问次数加一
if tonumber(current) == 1 then -- 如果是第一次访问就设置过期时间
    redis.call('expire', key, time)
end

return tonumber(current)