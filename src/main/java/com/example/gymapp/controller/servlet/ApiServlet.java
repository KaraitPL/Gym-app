package com.example.gymapp.controller.servlet;

import com.example.gymapp.gym.controller.api.GymController;
import com.example.gymapp.member.controller.api.MemberController;
import com.example.gymapp.member.dto.PatchMemberRequest;
import com.example.gymapp.member.dto.PutMemberRequest;
import com.example.gymapp.trainer.controller.api.TrainerController;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {
        ApiServlet.Paths.API + "/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class ApiServlet extends HttpServlet {

    private MemberController memberController;
    private GymController gymController;

    private TrainerController trainerController;
    public static final class Paths {

        public static final String API = "/api";

    }

    public static final class Patterns {


        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

        public static final Pattern MEMBERS = Pattern.compile("/members/?");

        public static final Pattern MEMBER = Pattern.compile("/members/(%s)".formatted(UUID.pattern()));

        public static final Pattern MEMBER_PORTRAIT = Pattern.compile("/members/(%s)/portrait".formatted(UUID.pattern()));

        public static final Pattern GYMS = Pattern.compile("/gyms/?");

        public static final Pattern TRAINER = Pattern.compile("/trainers/(%s)".formatted(UUID.pattern()));

        public static final Pattern TRAINERS = Pattern.compile("/trainers/?");

        public static final Pattern GYM_MEMBERS = Pattern.compile("/gyms/(%s)/members/?".formatted(UUID.pattern()));

        public static final Pattern USER_MEMBERS = Pattern.compile("/trainers/(%s)/members/?".formatted(UUID.pattern()));

    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        memberController = (MemberController) getServletContext().getAttribute("memberController");
        gymController = (GymController) getServletContext().getAttribute("gymController");
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.MEMBERS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(memberController.getMembers()));
                return;
            } else if (path.matches(Patterns.MEMBER.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.MEMBER, path);
                response.getWriter().write(jsonb.toJson(memberController.getMember(uuid)));
                return;
            } else if (path.matches(Patterns.GYMS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(gymController.getGyms()));
                return;
            } else if (path.matches(Patterns.GYM_MEMBERS.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.GYM_MEMBERS, path);
                response.getWriter().write(jsonb.toJson(memberController.getGymMembers(uuid)));
                return;
            } else if (path.matches(Patterns.USER_MEMBERS.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.USER_MEMBERS, path);
                response.getWriter().write(jsonb.toJson(memberController.getTrainerMembers(uuid)));
                return;
            } else if (path.matches(Patterns.TRAINER.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.TRAINER, path);
                response.getWriter().write(jsonb.toJson(trainerController.getTrainer(uuid)));
                return;
            } else if (path.matches(Patterns.TRAINERS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(trainerController.getTrainers()));
                return;
            } /*else if (path.matches(Patterns.MEMBER_PORTRAIT.pattern())) {
                response.setContentType("image/png");//could be dynamic but atm we support only one format
                UUID uuid = extractUuid(Patterns.MEMBER_PORTRAIT, path);
                byte[] portrait = memberController.getMemberPortrait(uuid);
                response.setContentLength(portrait.length);
                response.getOutputStream().write(portrait);
                return;
            }*/
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.MEMBER.pattern())) {
                UUID uuid = extractUuid(Patterns.MEMBER, path);
                memberController.putMember(uuid, jsonb.fromJson(request.getReader(), PutMemberRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "members", uuid.toString()));
                return;
            } /*else if (path.matches(Patterns.MEMBER_PORTRAIT.pattern())) {
                UUID uuid = extractUuid(Patterns.MEMBER_PORTRAIT, path);
                memberController.putMemberPortrait(uuid, request.getPart("portrait").getInputStream());
                return;
            }*/
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.MEMBER.pattern())) {
                UUID uuid = extractUuid(Patterns.MEMBER, path);
                memberController.deleteMember(uuid);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.MEMBER.pattern())) {
                UUID uuid = extractUuid(Patterns.MEMBER, path);
                memberController.patchMember(uuid, jsonb.fromJson(request.getReader(), PatchMemberRequest.class));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }

    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }

    public static String createUrl(HttpServletRequest request, String... paths) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath());
        for (String path : paths) {
            builder.append("/")
                    .append(path, path.startsWith("/") ? 1 : 0, path.endsWith("/") ? path.length() - 1 : path.length());
        }
        return builder.toString();
    }

}

