package com.example.gymapp.controller.servlet;

import com.example.gymapp.controller.servlet.exception.AlreadyExistsException;
import com.example.gymapp.controller.servlet.exception.BadRequestException;
import com.example.gymapp.controller.servlet.exception.NotFoundException;
import com.example.gymapp.gym.controller.api.GymController;
import com.example.gymapp.gym.dto.PutGymRequest;
import com.example.gymapp.member.controller.api.MemberController;
import com.example.gymapp.member.dto.PatchMemberRequest;
import com.example.gymapp.member.dto.PutMemberRequest;
import com.example.gymapp.trainer.controller.api.TrainerController;
import com.example.gymapp.trainer.dto.PatchTrainerRequest;
import com.example.gymapp.trainer.dto.PutTrainerRequest;
import jakarta.inject.Inject;
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

    private TrainerController trainerController;

    private MemberController memberController;

    private GymController gymController;

    private String avatarPath;

    public static final class Paths {
        public static final String API = "/api";
    }

    public static final class Patterns {
        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

        public static final Pattern TRAINER = Pattern.compile("/trainers/(%s)".formatted(UUID.pattern()));

        public static final Pattern TRAINERS = Pattern.compile("/trainers/?");

        public static final Pattern GYM = Pattern.compile("/gyms/(%s)".formatted(UUID.pattern()));

        public static final Pattern GYMS = Pattern.compile("/gyms/?");

        public static final Pattern TRAINER_MEMBERS = Pattern.compile("/trainers/(%s)/members/?".formatted(UUID.pattern()));

        public static final Pattern TRAINER_AVATAR = Pattern.compile("/trainers/(%s)/avatar".formatted(UUID.pattern()));

        public static final Pattern MEMBER = Pattern.compile("/members/(%s)".formatted(UUID.pattern()));

        public static final Pattern MEMBERS = Pattern.compile("/members/?");

        public static final Pattern GYM_MEMBERS = Pattern.compile("/gyms/(%s)/members/?".formatted(UUID.pattern()));
    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Inject
    public ApiServlet(MemberController memberController, GymController gymController, TrainerController trainerController) {
        this.memberController = memberController;
        this.gymController = gymController;
        this.trainerController = trainerController;
    }

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
        //trainerController = (TrainerController) getServletContext().getAttribute("trainerController");
        //memberController = (MemberController) getServletContext().getAttribute("memberController");
        //gymController = (GymController) getServletContext().getAttribute("gymController");
        avatarPath = (String) getServletContext().getInitParameter("avatars-upload");
        //System.out.println(avatarPath);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.TRAINERS.pattern())) {
                response.setContentType("application/json");
                try {
                    response.getWriter().write(jsonb.toJson(trainerController.getTrainers()));
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            } else if (path.matches(Patterns.TRAINER.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.TRAINER, path);
                try {
                    response.getWriter().write(jsonb.toJson(trainerController.getTrainer(uuid)));
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            } else if (path.matches(Patterns.TRAINER_MEMBERS.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.TRAINER_MEMBERS, path);
                try {
                    response.getWriter().write(jsonb.toJson(memberController.getTrainerMembers(uuid)));
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            } else if (path.matches(Patterns.TRAINER_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.TRAINER_AVATAR, path);
                response.setContentType("image/png");
                try {
                    byte[] avatar = trainerController.getTrainerAvatar(uuid, avatarPath);
                    response.setContentLength(avatar.length);
                    response.getOutputStream().write(avatar);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.MEMBER.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.MEMBER, path);
                try {
                    response.getWriter().write(jsonb.toJson(memberController.getMember(uuid)));
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            } else if (path.matches(Patterns.MEMBERS.pattern())) {
                response.setContentType("application/json");
                try {
                    response.getWriter().write(jsonb.toJson(memberController.getMembers()));
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            } else if (path.matches(Patterns.GYM.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.GYM, path);
                try {
                    response.getWriter().write(jsonb.toJson(gymController.getGym(uuid)));
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            } else if (path.matches(Patterns.GYMS.pattern())) {
                response.setContentType("application/json");
                try {
                    response.getWriter().write(jsonb.toJson(gymController.getGyms()));
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            } else if (path.matches(Patterns.GYM_MEMBERS.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.GYM_MEMBERS, path);
                try {
                    response.getWriter().write(jsonb.toJson(memberController.getGymMembers(uuid)));
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.TRAINER.pattern())) {
                UUID uuid = extractUuid(Patterns.TRAINER, path);
                try {
                    trainerController.putTrainer(uuid, jsonb.fromJson(request.getReader(), PutTrainerRequest.class));
                    response.addHeader("Location", createUrl(request, Paths.API, "trainers", uuid.toString()));
                    response.setStatus(HttpServletResponse.SC_CREATED);
                } catch (BadRequestException ex) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
                return;
            } else if (path.matches(Patterns.TRAINER_AVATAR.pattern())) {
                response.setContentType("image/png");
                UUID uuid = extractUuid(Patterns.TRAINER_AVATAR, path);
                try {
                    trainerController.putTrainerAvatar(uuid, request.getPart("avatar").getInputStream(), avatarPath);
                    response.setStatus(HttpServletResponse.SC_CREATED);
                } catch (AlreadyExistsException ex) {
                    response.sendError(HttpServletResponse.SC_CONFLICT, ex.getMessage());
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.MEMBER.pattern())) {
                UUID uuid = extractUuid(Patterns.MEMBER, path);
                try {
                    memberController.putMember(uuid, jsonb.fromJson(request.getReader(), PutMemberRequest.class));
                    response.addHeader("Location", createUrl(request, Paths.API, "members", uuid.toString()));
                    response.setStatus(HttpServletResponse.SC_CREATED);
                } catch (BadRequestException ex) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
                return;
            } else if (path.matches(Patterns.GYM.pattern())) {
                UUID uuid = extractUuid(Patterns.GYM, path);
                try {
                    gymController.putGym(uuid, jsonb.fromJson(request.getReader(), PutGymRequest.class));
                    response.addHeader("Location", createUrl(request, Paths.API, "gyms", uuid.toString()));
                    response.setStatus(HttpServletResponse.SC_CREATED);
                } catch (BadRequestException ex) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.TRAINER.pattern())) {
                UUID uuid = extractUuid(Patterns.TRAINER, path);
                try {
                    trainerController.deleteTrainer(uuid);
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            } else if (path.matches(Patterns.TRAINER_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.TRAINER_AVATAR, path);
                try {
                    trainerController.deleteTrainerAvatar(uuid, avatarPath);
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.MEMBER.pattern())) {
                UUID uuid = extractUuid(Patterns.MEMBER, path);
                try {
                    memberController.deleteMember(uuid);
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
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
            if (path.matches(Patterns.TRAINER.pattern())) {
                UUID uuid = extractUuid(Patterns.TRAINER, path);
                try {
                    trainerController.patchTrainer(uuid, jsonb.fromJson(request.getReader(), PatchTrainerRequest.class));
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            } else if (path.matches(Patterns.TRAINER_AVATAR.pattern())) {
                response.setContentType("image/png");
                UUID uuid = extractUuid(Patterns.TRAINER_AVATAR, path);
                try {
                    trainerController.patchTrainerAvatar(uuid, request.getPart("avatar").getInputStream(), avatarPath);
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.MEMBER.pattern())) {
                UUID uuid = extractUuid(Patterns.MEMBER, path);
                try {
                    memberController.patchMember(uuid, jsonb.fromJson(request.getReader(), PatchMemberRequest.class));
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
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

