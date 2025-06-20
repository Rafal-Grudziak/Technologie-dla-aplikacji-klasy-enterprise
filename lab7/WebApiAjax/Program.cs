var builder = WebApplication.CreateBuilder(args);
var app = builder.Build();

app.UseDefaultFiles();
app.UseStaticFiles();

app.MapGet("/math", (int x, int y) =>
{
    if (y == 0)
        return Results.BadRequest(new { error = "Division by zero" });

    var result = new
    {
        sum = x + y,
        difference = x - y,
        product = x * y,
        quotient = x / y
    };
    return Results.Json(result);
});

app.Run();
